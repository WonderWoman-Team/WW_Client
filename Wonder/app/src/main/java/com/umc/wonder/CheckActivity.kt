package com.umc.wonder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc.wonder.databinding.Login5MainBinding


class CheckActivity : AppCompatActivity() {
    private var mBinding: Login5MainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login5MainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, CheckActivity::class.java)
        binding.nextBtn5.setOnClickListener{startActivity(intent) }
    }}