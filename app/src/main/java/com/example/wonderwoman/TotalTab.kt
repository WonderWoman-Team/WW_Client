package com.example.wonderwoman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wonderwoman.databinding.TotalTabBinding

class TotalTab : Fragment(){
    private lateinit var totalTabBinding: TotalTabBinding

    companion object{
        fun newInstance() : TotalTab {
            return TotalTab()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        totalTabBinding = TotalTabBinding.inflate(inflater,container,false)
        return totalTabBinding.root
    }
}