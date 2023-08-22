package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.Login4MainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.annotations.SerializedName
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import com.example.wonderwoman.SignupRequest
import com.example.wonderwoman.SignupResponse
import com.example.wonderwoman.NicknameCheckRequest
import com.example.wonderwoman.NicknameCheckResponse

class NameActivity : AppCompatActivity() {
    private var mBinding: Login4MainBinding? = null
    private val binding get() = mBinding!!
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login4MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrofit 인스턴스
        val retrofit = Retrofit.Builder()
            .baseUrl("http://43.202.116.247:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // ApiService 인터페이스
        apiService = retrofit.create(ApiService::class.java)

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

        //다음페이지
        binding.nextBtn4.setOnClickListener{

            val client = OkHttpClient()
            val nickname = binding.namedata.text.toString() // EditText에서 받아온 닉네임 값
            val password = binding.pwdata.text.toString() // EditText에서 받아온 비밀번호 값
            val email = binding.emaildata2.text.toString() // EditText에서 받아온 이메일 값
            val school = binding.schooldata2.text.toString() // EditText에서 받아온 학교 값

            // 닉네임 중복 체크 요청
            val nicknameCheckRequest = NicknameCheckRequest(nickname)
            val nicknameCheckCall = apiService.checkNickname(nicknameCheckRequest)

            nicknameCheckCall.enqueue(object : Callback<NicknameCheckResponse> {
                override fun onResponse(call: Call<NicknameCheckResponse>, response: Response<NicknameCheckResponse>) {
                    if (response.isSuccessful) {
                        val nicknameCheckResponse = response.body()
                        Log.d("ServerResponse", "Nickname Check Response: $nicknameCheckResponse")
                        if (nicknameCheckResponse?.status == "SUCCESS") {
                            // 닉네임 사용 가능한 경우 회원가입 진행
                            val signupRequest = SignupRequest(nickname, password, email.trim(), school)
                            val signupCall = apiService.signup(signupRequest)

                            signupCall.enqueue(object : Callback<SignupResponse> {
                                override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                                    if (response.isSuccessful) {
                                        val signupResponse = response.body()
                                        // 회원가입 성공 처리
                                        val intent = Intent(this@NameActivity, CheckActivity::class.java)
                                        startActivity(intent)
                                        Toast.makeText(this@NameActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                                    } else {
                                        // 회원가입 실패 처리
                                        Toast.makeText(this@NameActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                                    // 네트워크 요청 실패 처리
                                    Toast.makeText(this@NameActivity, "네트워크를 확인해주세요.", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    }
                }
                override fun onFailure(call: Call<NicknameCheckResponse>, t: Throwable) {
                    // 네트워크 요청 실패 처리
                }
            })
        }
    }
}