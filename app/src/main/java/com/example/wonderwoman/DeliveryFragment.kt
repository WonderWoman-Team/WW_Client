package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.wonderwoman.databinding.FragmentDeliveryBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


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

        val intent = Intent(getActivity(), EwhaActivity::class.java)
        binding.scrollbtn.setOnClickListener{startActivity(intent)}

        return binding.root
    }
}

