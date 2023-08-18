package com.example.wonderwoman.model.delivery

import com.example.wonderwoman.util.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

//게시글 목록 관련 API를 정의하는 인터페이스
interface DeliveryService {
    @GET(Constants.GET_DELIVERY)
    fun getDeliveryList(
        @Header("Authorization") Authorization: String,
        @Query("reqType") reqType: String?,
        @Query("building") building: String?,
        @Query("size") size: List<String>,
        @Query("school") school: String?
        ): Call<ResponseDelivery>

    @POST(Constants.ADD_POST)
    fun addDeliveryPost(
        @Header("Authorization") Authorization: String,
        @Body requestAddPost: RequestAddPost
    ): Call<ResponseAddPost>
//    fun addDeliveryPost(
//        @Query("building") building: List<String>,
//        @Query("postTitle") postTitle: String,
//        @Query("postReqType") postReqType: String,
//        @Query("sanitaryNum") sanitaryNum: String,
//        @Query("sanitarySize") sanitarySize: String,
//        @Query("sanitaryType") sanitaryType: String,
//        @Query("postComment") postComment: String,
//    ): Call<ResponseAddPost>
}