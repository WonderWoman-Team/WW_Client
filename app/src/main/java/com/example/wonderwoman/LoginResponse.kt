package com.example.wonderwoman

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse(
//    @SerializedName("accessToken")
    var accessToken: String?,
//    @SerializedName("refreshToken")
    var refreshToken: String?,
): Serializable

data class LoginRequest(
    var email: String,
    var password: String
)