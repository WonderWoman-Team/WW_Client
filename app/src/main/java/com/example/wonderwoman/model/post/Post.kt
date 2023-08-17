package com.example.wonderwoman.model.post

//게시글 관련 데이터 클래스

//request 형식
data class Post(
    val status: String,
    val message: String?,
    val solution: String?
)

data class RequestPost(
    val building: List<String>,
    val postTitle: String,
    val postReqType: String,
    val sanitaryNum: Int,
    val sanitarySize: String,
    val sanitaryType: String,
    val postComment: String
)