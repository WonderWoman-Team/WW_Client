package com.umc.wonder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.wonder.databinding.Login22MainBinding

class EmailActivity : AppCompatActivity() {
    private var mBinding: Login22MainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login22MainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, PasswordActivity::class.java)
        binding.nextBtn2.setOnClickListener{startActivity(intent) }
    }}