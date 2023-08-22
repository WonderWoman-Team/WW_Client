package com.example.wonderwoman.model.mypage

import com.example.wonderwoman.model.delivery.ResponseDelivery
import com.example.wonderwoman.util.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

interface MyPageService {
    @GET(Constants.MYINFO)
    fun getMyInfo(
        @Header("Authorization") Authorization: String
    ): Call<ResponseMyInfo>

    @PUT(Constants.MYINFO)
    fun editMyInfo(
        @Header("Authorization") Authorization: String,
        @Body requestEditMyInfo: RequestEditMyInfo
    ): Call<ResponseMyInfo>
}