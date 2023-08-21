package com.example.wonderwoman

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiRoomStatus {
    @POST("app/delivery/room/status")
    fun getRoomStatus(
        @Header("Authorization") Authorization: String,
        @Header("Cookie") Cookie: String,
        @Body request: ChatRoomRequest
    ): Call<DataRoomStatus>
}
data class ChatRoomRequest(
    val chatRoomId: String,
    val status: String
)