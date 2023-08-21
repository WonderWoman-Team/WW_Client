package com.example.wonderwoman.model.mypage

data class ResponseMyInfo(
    var id: Int,
    var email: String,
    var nickname: String,
    var school: String,
    var imgUrl: String?
)

data class RequestEditMyInfo(
    var nickname: String?,
    var password: String?,
    var imgUrl: String?
)