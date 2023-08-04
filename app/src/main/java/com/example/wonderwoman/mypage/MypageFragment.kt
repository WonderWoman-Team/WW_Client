package com.example.wonderwoman.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wonderwoman.R

class MypageFragment: Fragment() {
    companion object {
        fun newInstance() : MypageFragment {
            return MypageFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mypage,container,false)
        return view
    }
}