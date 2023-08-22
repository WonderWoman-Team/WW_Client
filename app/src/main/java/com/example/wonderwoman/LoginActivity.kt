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
import com.example.wonderwoman.model.post.ResponseAddPost
import com.example.wonderwoman.util.Constants.BASE_URL
import com.example.wonderwoman.util.Constants.EWHA
import com.example.wonderwoman.util.Constants.SOOKMYUNG
import com.example.wonderwoman.util.CustomToast
import com.google.firebase.auth.FirebaseAuth
import okhttp3.Headers
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: LoginMainBinding
//        private val binding get() = mBinding!!
    private lateinit var retrofit: Retrofit
    private lateinit var apiService: ApiService

    private var loginRequest: LoginRequest = LoginRequest("","")
    private var responseToken: LoginResponse = LoginResponse(null,null)
    private lateinit var refreshToken: List<String>
    private var accessToken = ""
    private var school = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = LoginMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        Log.d("goto","login")
        // Retrofit 초기화
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(nullOnEmptyConverterFactory)
            .build()

        apiService = retrofit.create(ApiService::class.java)

        // 로그인 버튼
        mBinding.login.setOnClickListener {
            loginRequest.email = mBinding.editID.text.toString()
            loginRequest.password = mBinding.editPassword.text.toString()

            // 로그인 API 호출
            val call = apiService.login(loginRequest)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("ServerResponse", "login Response success: $response")
                        val loginResponse = response.body()
                        val result: Response<Void> = response
//                        Log.d("success", "login + ${result.code()} + ${result.body()} + ${result.raw()}")
                        refreshToken = result.headers().get("Set-Cookie")!!.split(";")
                        accessToken = result.headers().get("Authorization")!!
                        responseToken.refreshToken = refreshToken[0]
                        responseToken.accessToken = accessToken
                        Log.d("success", "login + ${accessToken[0]}+ ${refreshToken}")

                        if(loginRequest.email.contains("ewha")) school = EWHA
                        else school = SOOKMYUNG

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("token",responseToken.accessToken)
                        intent.putExtra("school",school)
                        startActivity(intent)
                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                        // TODO: 로그인 성공 처리
                    } else {
                        val result: Response<Void> = response
                        Log.d("error", "login + ${result.code()} + ${result.body()} + ${result.raw()}")

//                        Log.d("ServerResponse", "login Response fail: ${response.errorBody()}")
//                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                        startActivity(intent)
                        Toast.makeText(this@LoginActivity, "아이디 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                        // TODO: 로그인 실패 처리
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    t.message?.let { Log.d("fail login", t.message.toString()) }

                    Toast.makeText(this@LoginActivity, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
                    // TODO: 네트워크 요청 실패 처리
                }
            })
        }
    }
    private val nullOnEmptyConverterFactory = object : Converter.Factory() {
        fun converterFactory() = this
        override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
            val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
            override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
        }
    }
}



