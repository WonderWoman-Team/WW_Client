package com.example.wonderwoman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wonderwoman.databinding.DispatchTabBinding

class DispatchTab : Fragment(){
    private lateinit var dispatchTabBinding: DispatchTabBinding

    companion object{
        fun newInstance() : DispatchTab {
            return DispatchTab()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dispatchTabBinding = DispatchTabBinding.inflate(inflater,container,false)
        return dispatchTabBinding.root
    }
}