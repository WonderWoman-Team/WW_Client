package com.example.wonderwoman

data class Message(
    var message: String?,
    var sendId: String?
){
    constructor():this("","")
}
