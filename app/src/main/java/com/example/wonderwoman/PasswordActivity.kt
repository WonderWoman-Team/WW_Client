package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.Login3MainBinding

class PasswordActivity :AppCompatActivity() {
    private var mBinding: Login3MainBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login3MainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //다음페이지
        val intent = Intent(this, NameActivity::class.java)
        binding.nextBtn3.setOnClickListener{startActivity(intent) }
        //이전페이지
        val intent2 = Intent(this, EmailActivity::class.java)
        binding.back3.setOnClickListener{startActivity(intent2) }
    }}