package com.example.wonderwoman.model

import com.example.wonderwoman.model.mypage.MyPageService
import com.example.wonderwoman.model.delivery.DeliveryService
import com.example.wonderwoman.model.post.PostService
import com.example.wonderwoman.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//싱글톤 패턴을 이용한 retrofit 객체 생성
object RetrofitClass {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val deliveryAPI: DeliveryService = retrofit.create(DeliveryService::class.java)
    val postAPI: PostService = retrofit.create(PostService::class.java)
    val mypageAPI: MyPageService = retrofit.create(MyPageService::class.java)
}