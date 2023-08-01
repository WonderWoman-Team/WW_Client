package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.Login22MainBinding

class EmailActivity : AppCompatActivity() {
    private var mBinding: Login22MainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login22MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //다음페이지
        val intent = Intent(this, PasswordActivity::class.java)
        binding.nextBtn2.setOnClickListener{startActivity(intent) }
        //이전페이지
        val intent2 = Intent(this, SchoolActivity::class.java)
        binding.back2.setOnClickListener{startActivity(intent2) }
    }}