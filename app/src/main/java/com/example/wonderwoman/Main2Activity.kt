package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.AppMainBinding
import com.example.wonderwoman.databinding.Login21MainBinding


class Main2Activity : AppCompatActivity() {
    private var mBinding: AppMainBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = AppMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, SchoolActivity::class.java)
        binding.join.setOnClickListener{startActivity(intent) }

        val intent2 = Intent(this, LoginActivity::class.java)
        binding.logingo.setOnClickListener{startActivity(intent2) }

    }


}