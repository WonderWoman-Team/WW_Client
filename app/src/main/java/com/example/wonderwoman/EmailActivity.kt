package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.Login22MainBinding

class EmailActivity : AppCompatActivity() {
    private var mBinding: Login22MainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login22MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ArrayAdapter.createFromResource(
            this,
            R.array.university,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter
        }

        //다음페이지
        val intent = Intent(this, PasswordActivity::class.java)
        binding.nextBtn2.setOnClickListener{startActivity(intent) }
        //이전페이지
        val intent2 = Intent(this, SchoolActivity::class.java)
        binding.back2.setOnClickListener{startActivity(intent2) }
    }}