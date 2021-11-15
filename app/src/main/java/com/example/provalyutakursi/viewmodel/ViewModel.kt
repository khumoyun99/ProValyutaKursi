package com.example.provalyutakursi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.provalyutakursi.models.Currency
import com.example.provalyutakursi.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel:ViewModel() {

    private val TAG = "ViewModel"
    private lateinit var currencyList:List<Currency>

    fun getCurrency():LiveData<List<Currency>>{
        val list=MutableLiveData<List<Currency>>()

        ApiClient.apiService.getCurrency().enqueue(object :Callback<List<Currency>>{
            override fun onResponse(
                call: Call<List<Currency>>,
                response: Response<List<Currency>>
            ) {
                if(response.isSuccessful){
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

                    list.value = currencyList
                    Log.d(TAG, "onResponse: ${response.body()}")
                }else{
                    Log.d(TAG, "onResponse: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<List<Currency>>, t: Throwable) {
                Log.d(TAG, "onFailure:${t.message}")
            }
        })

        return list
    }
}