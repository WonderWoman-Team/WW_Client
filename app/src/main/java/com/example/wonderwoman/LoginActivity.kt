package com.example.wonderwoman
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.wonderwoman.databinding.LoginMainBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private var mBinding: LoginMainBinding? = null
    private val binding get() = mBinding!!
    private lateinit var auth: FirebaseAuth
    class LoginActivity : AppCompatActivity() {
        private var mBinding: LoginMainBinding? = null
        private val binding get() = mBinding!!
        private lateinit var retrofit: Retrofit
        private lateinit var apiService: ApiService

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            mBinding = LoginMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Retrofit 초기화
            retrofit = Retrofit.Builder()
                .baseUrl("http://43.202.116.247:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(ApiService::class.java)

            // 로그인 버튼
            binding.login.setOnClickListener {
                val email = binding.editID.text.toString()
                val password = binding.editPassword.text.toString()

                // 로그인 API 호출
                val call = apiService.login(email, password)
                call.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            Log.d("ServerResponse", "login Response success: $response")
                            val loginResponse = response.body()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                            // TODO: 로그인 성공 처리
                        } else {
                            Log.d("ServerResponse", "login Response fail: $response")
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this@LoginActivity, "아이디 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                            // TODO: 로그인 실패 처리
                        }
                    }
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
                        // TODO: 네트워크 요청 실패 처리
                    }
                })
            }
        }
    }
}



    
