package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.Login3MainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PasswordActivity :AppCompatActivity() {
    private var mBinding: Login3MainBinding? = null
    private val binding get() = mBinding!!
    lateinit var mauth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login3MainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mauth = Firebase.auth
        mauth = FirebaseAuth.getInstance()

        //이메일 값 넘겨받기
        val input = intent.getStringExtra("Email")
        binding.schooldata.setText(input)
        binding.schooldata.text= Editable.Factory.getInstance().newEditable(input)

        //다음페이지
        binding.nextBtn3.setOnClickListener{
            val emaillogin = binding.schooldata.getText().toString()
            val pwlogin = binding.editpw2.getText().toString()
            val intent = Intent(this, NameActivity::class.java)
            intent.putExtra("Email", emaillogin)
            intent.putExtra("Password", pwlogin)
            startActivity(intent) }

        //이전페이지
        val intent2 = Intent(this, SchoolActivity::class.java)
        binding.back3.setOnClickListener{startActivity(intent2) }
    }}