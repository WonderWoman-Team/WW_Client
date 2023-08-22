package com.example.wonderwoman.util

import com.example.wonderwoman.Data
import com.example.wonderwoman.R

object Constants {
    //API
//    const val BASE_URL = "http://ec2-43-202-116-247.ap-northeast-2.compute.amazonaws.com:8080/"
    const val BASE_URL = "http://43.202.116.247:8080/"

    //Delivery API
    const val GET_DELIVERY = "app/delivery/post"

    //Post API
    const val ADD_POST = "app/delivery"

    //myinfo API
    const val MYINFO = "app/member/myInfo"

    //Access Token => 토큰은 깃에 올리지 않기
    const val ACCESS_TOKEN =
        //sy
//        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJlbWFpbCI6InN5ZW9uMjVAbWp1LmFjLmtyIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTY5MTA5MzExNywiZXhwIjoxNjkxMTM2MzE3fQ.y9n7JwBcmCFncmlpVHAZoUWFtH1hIcHXPRzvPu7QJeLziaftZ4tsZsoR8fH-GncK4Tp6vW7ov8q9d77QZT0a7w"
//ahn
"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJlbWFpbCI6ImFobkBzbS5hYy5rciIsInJvbGUiOiLtmozsm5AiLCJpYXQiOjE2OTI2OTIyOTYsImV4cCI6MTY5MjczNTQ5Nn0.CSPws_hV9FhTZhhqegk4RnSY0g9z3Hk2-lH1xPcph1LRetA3d0LhKRnH6usPtJWG2qrpNt5qOqCBvVEVI2o5_g"

   const val REFRESH_TOKEN =
       ""
    //etc
    //dispatch
    const val DISPATCH = "출동"

    //request
    const val REQUEST = "요청"

    //ewha
    const val EWHA = "이화여자대학교"
    val EWHA_BUILDING = listOf(
        "ECC(이화캠퍼스복합단지)", "포스코관", "학문관(학생문화관)", "학관",
        "국교관(국제교육관)", "SK관(이화SK텔레콤관)", "경영관(이화신세계관)", "생활관(생활환경관)", "대강당",
        "중도(중앙도서관)", "음악관", "조형A(조형예술관A동)", "조형B(조형예술관B동)", "체육A(체육관A동)", "체육B(체육관B동)", "체육C(체육관C동)", "헬렌관",
        "약학관A동", "약학관B동", "종과A(종학과학관A동)", "종과B(종학과학관B동)", "종과C(종학과학관C동)", "종과D(종학과학관D동)",
        "교육A(교육관A동)", "교육B(교육관B동)", "공학A(아산공학관)", "공학B(신공학관)")
    //sookmyung
    const val SOOKMYUNG = "숙명여자대학교"
    val SOOKMYUNG_BUILDING = listOf(
        "순헌관", "명신관", "학생회관", "프라임관", "중앙도서관",
        "진리관", "새힘관", "행정관",
        "르네상스플라자", "음악대학", "사회교육관", "약학대학", "미술대학",
        "백주년기념관", "과학관", "다목적관", "새빛관")
}