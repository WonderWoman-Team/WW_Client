package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth

    private lateinit var mDbRef: DatabaseReference

    val name = "익명3"
    val email = "somin455@nate.com"
    val password = "somin455"

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        //인증초기화
        mAuth = Firebase.auth

        //db초기화
        mDbRef = Firebase.database.reference

        binding.button.setOnClickListener{
            signup(name,email,password)
        }
        binding.button2.setOnClickListener{
            login(email, password)
        }

    }
    //로그인

    private fun login(email: String,password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent2: Intent = Intent(this,Main1Activity::class.java)
                    startActivity(intent2)
                    finish()
                    Toast.makeText(this,"로그인성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,"로그인실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    //회원가입
    private fun signup(name: String, email: String,password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent: Intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    Toast.makeText(this,"회원가입성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,"회원가입실패",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUserToDatabase(name: String,email: String,uId: String){
        mDbRef.child("user").child(uId).setValue(User(name, email, uId))
        Toast.makeText(this,"db성공", Toast.LENGTH_SHORT).show()
    }
}