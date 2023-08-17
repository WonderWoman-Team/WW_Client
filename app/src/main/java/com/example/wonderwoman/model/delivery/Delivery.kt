package com.example.wonderwoman.model.delivery

import com.google.gson.annotations.SerializedName

//게시글 목록 관련 데이터 클래스

//response 형식
data class ResponseDelivery(
//    @SerializedName("content")
    val content: List<Delivery>,
    val pageable: Pageable,
    val first: Boolean,
    val last: Boolean,
    val sort: Sort,
    val number: Int,
    val size: Int,
    val numberOfElements: Int,
    val empty: Boolean
){
    data class Delivery(
//    @SerializedName("content")
        val id: Int,                 //게시물 id
//    @SerializedName("memberId")
        val memberId: Int,           //작성자 id
//    @SerializedName("nickname")
        val nickname: String,        //작성자 닉네임
//    @SerializedName("createdAt")
        val createdAt: String,       //게시물 작성 시간
//    @SerializedName("school")
        val school: String,          //학교명
//    @SerializedName("building")
        val building: List<String>,        //건물명
//    @SerializedName("postStatus")
        val postStatus: String,      //진행상태
//    @SerializedName("postTitle")
        val postTitle: String,       //게시글 제목
//    @SerializedName("postReqType")
        val postReqType: String?,     //게시물 유형
//    @SerializedName("sanitaryNum")
        val sanitaryNum: Int,        //생리대 개수
//    @SerializedName("sanitarySize")
        val sanitarySize: String,    //생리대 사이즈
//    @SerializedName("postReqType")
        val sanitaryType: String,    //생리대 유형
//    @SerializedName("postComment")
        val postComment: String,     //게시물 내용 및 특이사항
//    @SerializedName("written")
        val written: Boolean         //게시물 조회 시 사용
    )

    data class Pageable(
        val sort: Sort,              //
        val offset: Int,             //
        val pageSize: Int,           //
        val pageNumber: Int,         //
        val paged: Boolean,          //
        val unpaged: Boolean         //
    )

    data class Sort(
        val empty: Boolean,          //
        val unsorted: Boolean,       //
        val sorted: Boolean,         //
    )
}

data class RequestAddPost(
    val building: List<String>,
    var postTitle: String,
    var postReqType: String,
    var sanitaryNum: Int,
    var sanitarySize: String,
    var sanitaryType: String,
    val postComment: String
)

data class ResponseAddPost(
    val status: String,
    val message: String?,
    val solution: String?
)
