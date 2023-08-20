package com.example.wonderwoman

import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.MessageBinding

class MyDialog(private val context : AppCompatActivity) {

    private lateinit var binding : MessageBinding
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    fun show(content : String) {
        binding = MessageBinding.inflate(context.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(true)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히게 함
        dlg.show()
    }
}

