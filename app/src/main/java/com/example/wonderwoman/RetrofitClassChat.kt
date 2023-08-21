package com.example.wonderwoman

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClassChat {
    private val retrofit2 = Retrofit.Builder()
        .baseUrl("http://43.202.116.247:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiRooms: ApiRooms=retrofit2.create(ApiRooms::class.java)
    val apiRoomStatus: ApiRoomStatus= retrofit2.create(ApiRoomStatus::class.java)
}