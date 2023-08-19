package com.example.wonderwoman.model.post

//게시글 관련 데이터 클래스

//request 형식
data class ResponseAddPost(
    val status: String,
    val message: String?,
    val solution: String?
)

data class RequestAddPost(
    val school: String,
    var building: List<String>,
    var postTitle: String,
    var postReqType: String,
    var sanitaryNum: Int,
    var sanitarySize: String,
    var sanitaryType: String,
    var postComment: String
)