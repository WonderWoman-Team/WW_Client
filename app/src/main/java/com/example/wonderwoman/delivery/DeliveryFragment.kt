package com.example.wonderwoman.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wonderwoman.HomeTabAdapter
import com.example.wonderwoman.databinding.FragmentDeliveryBinding
import com.google.android.material.tabs.TabLayoutMediator


class DeliveryFragment: Fragment() {
    private lateinit var binding: FragmentDeliveryBinding
    private val tabTitle = listOf("전체","요청","출동")
    
    companion object {
        fun newInstance() : DeliveryFragment {
            return DeliveryFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryBinding.inflate(inflater,container,false)
        binding.viewpager.adapter = HomeTabAdapter(this)
        TabLayoutMediator(binding.tablayout, binding.viewpager){
            tab, position -> tab.text = tabTitle[position]
        }.attach()

        return binding.root
    }
}

