package com.example.wonderwoman

data class DataRooms(
    val statutus: String,
    val message: String,
    val list: List<list>
)
data class list(
    val id: String,
    val userId: Int,
    val userNickName: String,
    val userImg: String,
    val postStatus: String,
    val school: String,
    val building: List<String>,
    val sanitarySize: String,
    val sanitaryNum: Int,
    val createdAt: String,
    val updateAt: String,
    val lastMessage: String,
    val writer: Boolean
)
