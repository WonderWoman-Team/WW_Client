package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.Login5MainBinding

class CheckActivity : AppCompatActivity() {
    private var mBinding: Login5MainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login5MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //다음페이지
        val intent = Intent(this, CheckActivity::class.java)
        binding.nextBtn5.setOnClickListener{startActivity(intent) }

        //이전페이지
        val intent2 = Intent(this, NameActivity::class.java)
        binding.back5.setOnClickListener{startActivity(intent2) }
    }}