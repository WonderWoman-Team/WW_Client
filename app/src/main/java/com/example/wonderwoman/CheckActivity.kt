package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.Login5MainBinding

class CheckActivity : AppCompatActivity() {
    private var mBinding: Login5MainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login5MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //값 넘겨받기
        val input = intent.getStringExtra("School")
        binding.schooldata.setText(input)
        binding.schooldata.text= Editable.Factory.getInstance().newEditable(input)

        //다음페이지
        binding.nextBtn5.setOnClickListener{
            val school=binding.schooldata.getText().toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("School", school)
            startActivity(intent) }


        //이전페이지
        val intent2 = Intent(this, NameActivity::class.java)
        binding.back5.setOnClickListener{startActivity(intent2) }
    }}