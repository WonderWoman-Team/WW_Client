package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.Login4MainBinding
import com.google.firebase.auth.FirebaseAuth

class NameActivity : AppCompatActivity() {
    private var mBinding: Login4MainBinding? = null
    private val binding get() = mBinding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login4MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //값 불러오기
        val input = intent.getStringExtra("Email")
        binding.emaildata2.setText(input)
        binding.emaildata2.text= Editable.Factory.getInstance().newEditable(input)
        val input2 = intent.getStringExtra("Password")
        binding.pwdata.setText(input2)
        binding.pwdata.text= Editable.Factory.getInstance().newEditable(input2)
        val input3 = intent.getStringExtra("School")
        binding.schooldata2.setText(input3)
        binding.schooldata2.text= Editable.Factory.getInstance().newEditable(input3)

        auth = FirebaseAuth.getInstance()

        //다음페이지
        binding.nextBtn4.setOnClickListener {
            val email = binding.emaildata2.text.toString().trim()
            val password = binding.pwdata.text.toString().trim()

            // Validate...
            createUser(email, password)
        }
    }
    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    val school = binding.schooldata2.text.toString() // EditText에서 받아온 학교 값
                    val intent = Intent(this, CheckActivity::class.java)
                    intent.putExtra("School", school)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
            }

        //이전페이지
        val intent2 = Intent(this, PasswordActivity::class.java)
        binding.back4.setOnClickListener{startActivity(intent2) }
    }}