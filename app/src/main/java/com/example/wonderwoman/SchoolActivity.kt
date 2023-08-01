package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.wonderwoman.databinding.Login21MainBinding

class SchoolActivity : AppCompatActivity() {
    private var mBinding: Login21MainBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login21MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //다음페이지
        val intent = Intent(this, EmailActivity::class.java)
        binding.nextBtn1.setOnClickListener{startActivity(intent) }

        //이전페이지
        val intent2 = Intent(this, Main2Activity::class.java)
        binding.back1.setOnClickListener{startActivity(intent2) }

        val editEmail = findViewById<EditText>(R.id.editEmail)
        val btnSend = findViewById<ImageView>(R.id.btnSend)
        // 버튼 이벤트
        btnSend.setOnClickListener() {
            Toast
                .makeText(this, "toast message", Toast.LENGTH_SHORT)
                .show()
        }
    }
}





