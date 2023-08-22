package com.example.wonderwoman

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    // 로그인 API 호출
    @POST("app/auth/login") // 로그인 API 엔드포인트 경로로 변경
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<Void>

    // 닉네임 중복 체크 API
    @POST("app/auth/signup/nickname-check")
    fun checkNickname(@Body request: NicknameCheckRequest): Call<NicknameCheckResponse>

    // 회원가입 API
    @POST("app/auth/signup")
    fun signup(@Body request: SignupRequest): Call<SignupResponse>
}