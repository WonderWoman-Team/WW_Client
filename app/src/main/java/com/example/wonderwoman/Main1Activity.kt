package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.LogoMainBinding

class Main1Activity : AppCompatActivity() {
    private var mBinding: LogoMainBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = LogoMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, Main2Activity::class.java)
        binding.touch.setOnClickListener{startActivity(intent) }
    }}