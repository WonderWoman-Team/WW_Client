package com.umc.wonder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.wonder.databinding.Login4MainBinding

class NameActivity : AppCompatActivity() {
    private var mBinding: Login4MainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login4MainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, CheckActivity::class.java)
        binding.nextBtn4.setOnClickListener{startActivity(intent) }
    }}