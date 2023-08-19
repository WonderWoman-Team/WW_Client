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
        val id: Int,                 //게시물 id
        val memberId: Int,           //작성자 id
        val nickname: String,        //작성자 닉네임
        val createdAt: String,       //게시물 작성 시간
        val school: String,          //학교명
        val building: List<String>,  //건물명
        val postStatus: String,      //진행상태
        val postTitle: String,       //게시글 제목
        val postReqType: String,     //게시물 유형
        val sanitaryNum: Int,        //생리대 개수
        val sanitarySize: String,    //생리대 사이즈
        val sanitaryType: String,    //생리대 유형
        val postComment: String,     //게시물 내용 및 특이사항
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
        val empty: Boolean,
        val unsorted: Boolean,
        val sorted: Boolean,
    )
}
