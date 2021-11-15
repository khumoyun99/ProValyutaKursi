package com.example.provalyutakursi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.provalyutakursi.database.AppDatabase
import com.example.provalyutakursi.databinding.ActivityMainBinding
import com.example.provalyutakursi.models.Currency
import com.example.provalyutakursi.service.MyWorkManager
import com.example.provalyutakursi.viewmodel.MyViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var myViewModel: MyViewModel
    private lateinit var currencyList:List<Currency>
    private lateinit var appDatabase:AppDatabase

    private val TAG = "MainActivity"


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBarLayout.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        appDatabase = AppDatabase.getInstance(this)

        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]
        myViewModel.getCurrency().observe(this,{
            currencyList = ArrayList(it)
            for (i in currencyList.indices){
                currencyList[i].photoUrl = "https://nbu.uz/local/templates/nbu/images/flags/${currencyList[i].code}.png"

                if (currencyList[i].nbu_buy_price==""){
                    currencyList[i].nbu_buy_price = currencyList[i].cb_price
                }

                if(currencyList[i].nbu_cell_price==""){
                    currencyList[i].nbu_cell_price = currencyList[i].cb_price
                }
            }
            currencyList.forEach {currency->
                appDatabase.currencyDao().addCurrency(currency)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(TAG, "onResponse: Ma'lumot yuklab olindi!!!")
                    },{
                        Log.d(TAG, "onResponse: Ma'lumot yuklanmadi!!!")
                    })
            }

        })

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()

        val workRequest = PeriodicWorkRequest
            .Builder(MyWorkManager::class.java,15,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this)
            .enqueue(workRequest)


    }


    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.my_nav_fragment).navigateUp()
    }

}