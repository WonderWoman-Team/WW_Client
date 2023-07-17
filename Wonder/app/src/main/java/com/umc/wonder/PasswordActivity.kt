package com.umc.wonder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.wonder.databinding.Login3MainBinding

class PasswordActivity :AppCompatActivity() {
    private var mBinding: Login3MainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login3MainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, NameActivity::class.java)
        binding.nextBtn3.setOnClickListener{startActivity(intent) }
    }}