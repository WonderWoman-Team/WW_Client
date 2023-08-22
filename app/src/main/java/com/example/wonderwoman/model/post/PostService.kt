package com.example.wonderwoman.model.post

import com.example.wonderwoman.util.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PostService {
    @POST(Constants.ADD_POST)
    fun addDeliveryPost(
        @Header("Authorization") Authorization: String,
        @Body requestAddPost: RequestAddPost
    ): Call<ResponseAddPost>
}