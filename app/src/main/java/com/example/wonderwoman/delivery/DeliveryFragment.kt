package com.example.wonderwoman.delivery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wonderwoman.EwhaActivity
import com.example.wonderwoman.HomeTabAdapter
import com.example.wonderwoman.MainActivity
import com.example.wonderwoman.SookmyungActivity
import com.example.wonderwoman.databinding.FragmentDeliveryBinding
import com.example.wonderwoman.util.Constants.EWHA
import com.google.android.material.tabs.TabLayoutMediator


class DeliveryFragment : Fragment() {
    private lateinit var binding: FragmentDeliveryBinding
    private val tabTitle = listOf("전체", "요청", "출동")
    private lateinit var school: String
    private var building: String = "전체"

    companion object {
        fun newInstance(): DeliveryFragment {
            return DeliveryFragment()
        }
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        school = (activity as MainActivity).getSchool()
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        school = (activity as MainActivity).getSchool()

        binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        binding.viewpager.adapter = HomeTabAdapter(this)
        TabLayoutMediator(binding.tablayout, binding.viewpager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
        val intent: Intent
        if(school == EWHA) intent = Intent(getActivity(), EwhaActivity::class.java)
        else intent = Intent(getActivity(), SookmyungActivity::class.java)
//        binding.buildingName.text = arguments?.getString("building") ?: ""
        binding.selectBuilding.setOnClickListener {
            startActivity(intent)
            binding.buildingName.text = arguments?.getString("selected") ?: "전체"
        }

        return binding.root
    }

}

