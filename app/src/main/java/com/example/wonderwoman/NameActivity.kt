package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.Login4MainBinding

class NameActivity : AppCompatActivity() {
    private var mBinding: Login4MainBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login4MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //다음페이지
        val intent = Intent(this, CheckActivity::class.java)
        binding.nextBtn4.setOnClickListener{startActivity(intent) }

        //이전페이지
        val intent2 = Intent(this, PasswordActivity::class.java)
        binding.back4.setOnClickListener{startActivity(intent2) }
    }}