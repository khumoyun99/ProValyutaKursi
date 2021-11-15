package com.example.provalyutakursi.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.provalyutakursi.R
import com.example.provalyutakursi.adapters.CurrencyRvAdapter
import com.example.provalyutakursi.database.AppDatabase
import com.example.provalyutakursi.databinding.FragmentAllCurrencyBinding
import com.example.provalyutakursi.models.Currency
import com.example.provalyutakursi.utils.NetworkHelper
import com.example.provalyutakursi.viewmodel.MyViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AllCurrencyFragment : Fragment() {

    private lateinit var binding:FragmentAllCurrencyBinding
    private lateinit var currencyRvAdapter: CurrencyRvAdapter
    private lateinit var currencyList: List<Currency>
    private lateinit var viewModel: MyViewModel
    private lateinit var networkHelper: NetworkHelper
    private lateinit var appDatabase:AppDatabase


    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_currency,container,false)
        networkHelper = NetworkHelper(requireContext())
        appDatabase = AppDatabase.getInstance(requireContext())
        currencyList = ArrayList()


        if(networkHelper.isNetworkConnected()){
            viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
            viewModel.getCurrency().observe(requireActivity(), Observer{
                currencyList = ArrayList(it)
                currencyRvAdapter = CurrencyRvAdapter(currencyList,
                    object : CurrencyRvAdapter.OnItemClickListener {
                        override fun onCalcClick(currency: Currency) {
                            val bundle = Bundle()
                            bundle.putSerializable("currency", currency)
                            Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
                        }
                    })
                binding.currencyRvAdapterData = currencyRvAdapter

            })

        }else{
            appDatabase.currencyDao().getAllCurrency()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    currencyRvAdapter = CurrencyRvAdapter(it, object : CurrencyRvAdapter.OnItemClickListener {
                            override fun onCalcClick(currency: Currency) {
                                val bundle = Bundle()
                                bundle.putSerializable("currency", currency)
                                Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                    binding.currencyRvAdapterData = currencyRvAdapter
                }
        }


        return binding.root
    }

}