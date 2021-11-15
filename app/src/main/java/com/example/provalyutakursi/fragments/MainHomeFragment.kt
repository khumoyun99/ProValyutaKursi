package com.example.provalyutakursi.fragments

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.provalyutakursi.R
import com.example.provalyutakursi.adapters.CurrencyRvAdapter
import com.example.provalyutakursi.adapters.PlastikViewPagerAdapter
import com.example.provalyutakursi.broadcast.MyReceiver
import com.example.provalyutakursi.database.AppDatabase
import com.example.provalyutakursi.databinding.FragmentMainHomeBinding
import com.example.provalyutakursi.databinding.ItemTabBinding
import com.example.provalyutakursi.models.Currency
import com.example.provalyutakursi.retrofit.ApiClient
import com.example.provalyutakursi.utils.NetworkHelper
import com.example.provalyutakursi.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainHomeFragment : Fragment() {

    private lateinit var binding:FragmentMainHomeBinding
    private lateinit var plastikViewPagerAdapter: PlastikViewPagerAdapter
    private lateinit var currencyList:ArrayList<Currency>
    private lateinit var myViewModel: MyViewModel
    private lateinit var networkHelper: NetworkHelper
    private lateinit var appDatabase:AppDatabase
    private lateinit var myReceiver:MyReceiver
    private val TAG = "MainHomeFragment"

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainHomeBinding.inflate(inflater,container,false)
        networkHelper = NetworkHelper(requireActivity())
        appDatabase = AppDatabase.getInstance(requireContext())
        currencyList = ArrayList()
        myReceiver = MyReceiver()

        if(networkHelper.isNetworkConnected()){
            myViewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
            myViewModel.getCurrency().observe(requireActivity(), Observer{
                currencyList = ArrayList(it)
                plastikViewPagerAdapter = PlastikViewPagerAdapter(currencyList,childFragmentManager)
                binding.plastikViewPager.adapter = plastikViewPagerAdapter
                binding.tabLayout.setupWithViewPager(binding.plastikViewPager)
//                binding.tabLayoutRing.setupWithViewPager(binding.plastikViewPager)
            })
        }else{
            appDatabase.currencyDao().getAllCurrency()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    plastikViewPagerAdapter = PlastikViewPagerAdapter(it,childFragmentManager)
                    binding.plastikViewPager.adapter = plastikViewPagerAdapter
                    binding.tabLayout.setupWithViewPager(binding.plastikViewPager)
//                    binding.tabLayoutRing.setupWithViewPager(binding.plastikViewPager)
                }
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        activity?.registerReceiver(myReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))   // internet bor yoki yo'qligi
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(myReceiver)
    }

}