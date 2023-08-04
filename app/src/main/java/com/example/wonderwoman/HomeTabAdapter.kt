package com.example.wonderwoman

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wonderwoman.delivery.DeliveryFragment
import com.example.wonderwoman.delivery.DispatchTab
import com.example.wonderwoman.delivery.RequestTab
import com.example.wonderwoman.delivery.TotalTab


class HomeTabAdapter(fragment: DeliveryFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TotalTab.newInstance()
            1 -> RequestTab.newInstance()
            else -> DispatchTab.newInstance()
        }
    }
}