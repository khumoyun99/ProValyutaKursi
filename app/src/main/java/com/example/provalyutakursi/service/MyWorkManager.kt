package com.example.provalyutakursi.service

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.provalyutakursi.database.AppDatabase
import com.example.provalyutakursi.models.Currency
import com.example.provalyutakursi.retrofit.ApiClient
import com.example.provalyutakursi.retrofit.ApiService
import com.example.provalyutakursi.utils.NetworkHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyWorkManager(val context: Context,workerParameters: WorkerParameters):
    Worker(context,workerParameters) {

    private lateinit var appDatabase: AppDatabase
    private lateinit var apiService: ApiService
    private lateinit var networkHelper: NetworkHelper
    private val TAG = "MyWorkManager"
    lateinit var currencyList:List<Currency>

    override fun doWork(): Result {
        Log.d(TAG, "doWork: ")
        apiService = ApiClient.apiService
        appDatabase = AppDatabase.getInstance(context)
        networkHelper = NetworkHelper(context)
        currencyList = ArrayList()

        if (networkHelper.isNetworkConnected()) {
            apiService.getCurrency().enqueue(object : Callback<List<Currency>> {
                @SuppressLint("CheckResult")
                override fun onResponse(
                    call: Call<List<Currency>>,
                    response: Response<List<Currency>>
                ) {
                    if (response.isSuccessful) {
                        currencyList = response.body()!!
                        for (i in currencyList.indices){
                            currencyList[i].photoUrl = "https://nbu.uz/local/templates/nbu/images/flags/${currencyList[i].code}.png"
                            if (currencyList[i].nbu_buy_price==""){
                                currencyList[i].nbu_buy_price = currencyList[i].cb_price
                            }
                            if(currencyList[i].nbu_cell_price==""){
                                currencyList[i].nbu_cell_price = currencyList[i].cb_price
                            }
                        }
                        currencyList.forEach {
                            appDatabase.currencyDao().addCurrency(it)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    Log.d(TAG, "onResponse: Ma'lumot yangilandi!!!")
                                },{
                                    Log.d(TAG, "onResponse: Ma'lumot yangilanmadi!!!")
                                })
                        }
                    } else {
                        Log.d(TAG, "onResponse: ${response.errorBody()}")
                      }
                }

                override fun onFailure(call: Call<List<Currency>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
        }

        return Result.success()
    }
}