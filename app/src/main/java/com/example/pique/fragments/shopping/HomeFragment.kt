package com.example.pique.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pique.R
import com.example.pique.adapters.HomeViewPagerAdapter
import com.example.pique.databinding.FragmentHomeBinding
import com.example.pique.fragments.categories.AppliancesCategoryFragment
import com.example.pique.fragments.categories.LaptopCategoryFragment
import com.example.pique.fragments.categories.MainCategoryFragment
import com.example.pique.fragments.categories.MobileAccessoriesCategoryFragment
import com.example.pique.fragments.categories.TvCategoryFragment
import com.example.pique.fragments.categories.WatchCategoryFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragment = arrayListOf<Fragment>(
            MainCategoryFragment(),
            LaptopCategoryFragment(),
            TvCategoryFragment(),
            WatchCategoryFragment(),
            MobileAccessoriesCategoryFragment(),
            AppliancesCategoryFragment()
        )

        binding.viewPager.isUserInputEnabled = false

        val viewPager2Adapter = HomeViewPagerAdapter(categoriesFragment,childFragmentManager,lifecycle)
        binding.viewPager.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab, position->
            when(position){
                0 -> tab.text = "Main"
                1 -> tab.text = "Laptop"
                2 -> tab.text = "Tv"
                3 -> tab.text = "Watch"
                4 -> tab.text = "Mobile Accessory"
                5 -> tab.text = "Appliance"
            }
        }.attach()
    }
}