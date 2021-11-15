package com.example.provalyutakursi.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.provalyutakursi.adapters.CurrencySpinnerAdapter
import com.example.provalyutakursi.database.AppDatabase
import com.example.provalyutakursi.databinding.FragmentCalculatorBinding
import com.example.provalyutakursi.models.Currency
import com.example.provalyutakursi.utils.NetworkHelper
import com.example.provalyutakursi.viewmodel.MyViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CalculatorFragment : Fragment() {

    private lateinit var binding:FragmentCalculatorBinding
    private lateinit var currencySpinnerAdapter: CurrencySpinnerAdapter
    private lateinit var currencyList: List<Currency>
    private lateinit var myViewModel: MyViewModel
    private lateinit var networkHelper: NetworkHelper
    private lateinit var appDatabase: AppDatabase
    private val TAG = "CalculatorFragment"


    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        binding = FragmentCalculatorBinding.inflate(inflater,container,false)
        currencyList = ArrayList()
        networkHelper = NetworkHelper(requireActivity())
        appDatabase = AppDatabase.getInstance(requireContext())

        if(networkHelper.isNetworkConnected()){
            myViewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
            myViewModel.getCurrency().observe(requireActivity(), {
                currencyList = ArrayList(it)
                currencySpinnerAdapter = CurrencySpinnerAdapter(currencyList)
                binding.currencySp.adapter = currencySpinnerAdapter
                binding.currencySp1.adapter = currencySpinnerAdapter

                binding.currencyValue.addTextChangedListener {text->
                    if(text?.isNotEmpty() == true){
                        val spinnerValue = binding.currencySp.selectedItemPosition
                        val spinnerValue1 = binding.currencySp1.selectedItemPosition
                        val number = text.toString()
                        val value = number.toInt() * currencyList[spinnerValue].cb_price.toDouble() / currencyList[spinnerValue1].cb_price.toDouble()
                        binding.qiymatTv.text = String.format("%1.3f",value)
                    }else{
                        binding.qiymatTv.text = "00.00"
                    }
                }

                binding.currencySp1.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        binding.buyTv.text = currencyList[position].nbu_buy_price
                        binding.sellTv.text = currencyList[position].nbu_cell_price
                        binding.currencyValue.setText("")
                        binding.qiymatTv.text = "00.00"
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }

            })

        }else{
            appDatabase.currencyDao().getAllCurrency()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    currencySpinnerAdapter = CurrencySpinnerAdapter(it)
                    binding.currencySp.adapter = currencySpinnerAdapter
                    binding.currencySp1 .adapter = currencySpinnerAdapter

                    binding.currencyValue.addTextChangedListener {text->
                        if(text?.isNotEmpty() == true){
                            val spinnerValue = binding.currencySp.selectedItemPosition
                            val spinnerValue1 = binding.currencySp1.selectedItemPosition
                            val number = text.toString()
                            val value =
                                number.toInt() * currencyList[spinnerValue].cb_price.toDouble() /
                                        currencyList[spinnerValue1].cb_price.toDouble()
                            binding.qiymatTv.text = String.format("%1.3f",value)
                        }else{
                            binding.qiymatTv.text = "00.00"
                        }
                    }

//                    binding.currencySp1.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
//                        override fun onItemSelected(
//                            parent: AdapterView<*>?,
//                            view: View?,
//                            position: Int,
//                            id: Long
//                        ) {
//                            binding.buyTv.text = currencyList[position].nbu_buy_price
//                            binding.sellTv.text = currencyList[position].nbu_cell_price
//                            binding.currencyValue.setText("")
//                            binding.qiymatTv.text = "00.00"
//                        }
//                        override fun onNothingSelected(parent: AdapterView<*>?) {
//
//                        }
//                    }


                }

        }

        return binding.root
    }

}