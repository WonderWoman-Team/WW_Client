package com.example.wonderwoman.model.delivery

import com.example.wonderwoman.util.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//게시글 목록 관련 API를 정의하는 인터페이스
interface DeliveryService {
    @GET(Constants.GET_DELIVERY)
    fun getDeliveryList(
        @Query("reqType") reqType: String?,
        @Query("building") building: String?,
        @Query("size") size: List<String>,
        @Query("school") school: String?
        ): Call<ResponseDelivery>
}