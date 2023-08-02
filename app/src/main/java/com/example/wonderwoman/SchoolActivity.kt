package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.wonderwoman.databinding.Login21MainBinding

class SchoolActivity : AppCompatActivity() {
    private var mBinding: Login21MainBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login21MainBinding.inflate(layoutInflater)
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
        val intent = Intent(this, EmailActivity::class.java)
        binding.nextBtn1.setOnClickListener{startActivity(intent) }

        //이전페이지
        val intent2 = Intent(this, Main2Activity::class.java)
        binding.back1.setOnClickListener{startActivity(intent2) }

        val editEmail = findViewById<EditText>(R.id.editEmail)
        val btnSend = findViewById<ImageView>(R.id.btnSend)
        // 버튼 이벤트
        btnSend.setOnClickListener() {
            Toast
                .makeText(this, "인증번호가 메일로 전송되었습니다.", Toast.LENGTH_SHORT)
                .show()
        }
    }
}





