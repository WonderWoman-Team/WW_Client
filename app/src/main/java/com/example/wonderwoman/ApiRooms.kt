package com.example.wonderwoman


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface ApiRooms {
    @GET("app/delivery/rooms")
    fun getUser(
        @Header("Authorization") Authorization: String
    ): Call<DataRooms>
    //call<User>

}