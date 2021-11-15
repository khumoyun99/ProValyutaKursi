package com.example.provalyutakursi.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.provalyutakursi.fragments.ItemViewPagerPlastikFragment
import com.example.provalyutakursi.models.Currency

class PlastikViewPagerAdapter(private val list:List<Currency>, fragmentManager: FragmentManager):
    FragmentPagerAdapter(fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return ItemViewPagerPlastikFragment.newInstance(list[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return  list[position].code
    }
}