package com.example.provalyutakursi.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.provalyutakursi.fragments.AllCurrencyFragment
import com.example.provalyutakursi.fragments.CalculatorFragment
import com.example.provalyutakursi.fragments.MainHomeFragment


class ContentViewPagerAdapter(fm:FragmentManager) :
    FragmentStatePagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> MainHomeFragment()
            1 -> AllCurrencyFragment()
            else -> CalculatorFragment()
        }
    }
}