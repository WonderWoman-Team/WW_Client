package com.example.wonderwoman.model.mypage

data class ResponseMyInfo(
    var id: Int,
    var email: String,
    var nickname: String,
    var school: String,
    var imgUrl: String?
)