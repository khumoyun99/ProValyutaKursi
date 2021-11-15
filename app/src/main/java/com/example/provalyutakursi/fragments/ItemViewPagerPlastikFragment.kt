package com.example.provalyutakursi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.provalyutakursi.databinding.FragmentItemViewPagerPlastikBinding
import com.example.provalyutakursi.models.Currency

private const val ARG_PARAM1 = "param1"

class ItemViewPagerPlastikFragment : Fragment() {

    private var param1: Currency? = null
    private lateinit var binding:FragmentItemViewPagerPlastikBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Currency?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? {
        binding = FragmentItemViewPagerPlastikBinding.inflate(inflater,container,false)

        binding.apply {
            dateTv.text = param1?.date.toString().substring(0,10)
            sotilishQiymatiTv.text = param1?.nbu_cell_price
            olinishiQiymatiTv.text = param1?.nbu_buy_price
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Currency) =
                ItemViewPagerPlastikFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM1, param1)
                    }
                }
    }
}