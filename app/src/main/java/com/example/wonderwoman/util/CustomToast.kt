package com.example.wonderwoman.util

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.example.wonderwoman.R

object CustomToast {
    fun showToast(context: Context, message: String) {

        val inflater = LayoutInflater.from(context)
        var v1 = inflater.inflate(R.layout.toast, null) // 팝업 시킬 뷰 생성

        var text: TextView = v1.findViewById(R.id.toast)
        text.text = message
        text.setTextColor(Color.BLACK)

        var t2 = Toast(context)
        t2.setGravity(Gravity.TOP, 0, 0)
        t2.view = v1
        t2.show()
    }

}