package com.example.provalyutakursi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.provalyutakursi.MainActivity
import com.example.provalyutakursi.R
import com.example.provalyutakursi.adapters.ContentViewPagerAdapter
import com.example.provalyutakursi.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager.widget.ViewPager




class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var contentViewPagerAdapter: ContentViewPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater,container,false)


        contentViewPagerAdapter = ContentViewPagerAdapter(childFragmentManager)
        binding.contentViewPager.adapter = contentViewPagerAdapter
        binding.bottomNavView.itemIconTintList = null

        binding.contentViewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                    when(position){
                        0 -> binding.bottomNavView.selectedItemId = R.id.main_menu
                        1 -> binding.bottomNavView.selectedItemId = R.id.all_currency
                        2 -> binding.bottomNavView.selectedItemId = R.id.calculator
                    }

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.main_menu ->  binding.contentViewPager.setCurrentItem(0,  false)
                R.id.all_currency -> binding.contentViewPager.setCurrentItem(1,false)
                R.id.calculator -> binding.contentViewPager.setCurrentItem(2,false)
            }
            true
        }




        return binding.root

    }

}