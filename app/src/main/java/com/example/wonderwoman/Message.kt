package com.example.wonderwoman


data class Message(
    var type: String,
//    var senderId: String,
//    var senderNickname: String,
    var message: String?,
    var roomId: String,
//    var imgUrl: String,
//    var sendTime: String
){
    constructor():this("","","")
}
