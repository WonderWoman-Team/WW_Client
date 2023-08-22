package com.example.wonderwoman

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("school")
    val school: String
)

data class SignupResponse(
    val status: Int,
    val message: String?,
    val solution: String?
)
data class NicknameCheckRequest(
    @SerializedName("nickname")
    val nickname: String
)

data class NicknameCheckResponse(
    val status: String,
    val message: String?
)