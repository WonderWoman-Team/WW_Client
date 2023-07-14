package com.example.wonderwoman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wonderwoman.databinding.RequestTabBinding

class RequestTab : Fragment(){
    private lateinit var requestTabBinding: RequestTabBinding

    companion object{
        fun newInstance() : RequestTab {
            return RequestTab()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestTabBinding = RequestTabBinding.inflate(inflater,container,false)
        return requestTabBinding.root
    }
}