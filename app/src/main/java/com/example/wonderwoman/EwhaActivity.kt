package com.example.wonderwoman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wonderwoman.databinding.EwhaLocationBinding

class EwhaActivity : AppCompatActivity() {

    val TAG: String = "로그"

    private var vBinding : EwhaLocationBinding? = null
    private val binding get() = vBinding!!

    var Data1List = arrayListOf(
        Data(R.drawable.list_icon, "전체"))
    var Data2List = arrayListOf(
        Data(R.drawable.list_icon, "ECC(이화캠퍼스복합단지)"),
        Data(R.drawable.list_icon, "포스코관"),
        Data(R.drawable.list_icon, "학문관(학생문화관)"),
        Data(R.drawable.list_icon, "학관"))
    var Data3List = arrayListOf(
        Data(R.drawable.list_icon, "국교관(국제교육관)"),
        Data(R.drawable.list_icon, "SK관(이화SK텔레콤관)"),
        Data(R.drawable.list_icon, "경영관(이화신세계관)"),
        Data(R.drawable.list_icon, "생활관(생활환경관)"),
        Data(R.drawable.list_icon, "대강당"))
    var Data4List = arrayListOf(
        Data(R.drawable.list_icon, "중도(중앙도서관)"),
        Data(R.drawable.list_icon, "음악관"),
        Data(R.drawable.list_icon, "조형A(조형예술관A동)"),
        Data(R.drawable.list_icon, "조형B(조형예술관B동)"),
        Data(R.drawable.list_icon, "체육A(체육관A동)"),
        Data(R.drawable.list_icon, "체육B(체육관B동)"),
        Data(R.drawable.list_icon, "체육C(체육관C동)"),
        Data(R.drawable.list_icon, "헬렌관"))
    var Data5List = arrayListOf(
        Data(R.drawable.list_icon, "약학관A동"),
        Data(R.drawable.list_icon, "약학관B동"),
        Data(R.drawable.list_icon, "종과A(종학과학관A동)"),
        Data(R.drawable.list_icon, "종과B(종학과학관B동)"),
        Data(R.drawable.list_icon, "종과C(종학과학관C동)"),
        Data(R.drawable.list_icon, "종과D(종학과학관D동)"))
    var Data6List = arrayListOf(
        Data(R.drawable.list_icon, "교육A(교육관A동)"),
        Data(R.drawable.list_icon, "교육B(교육관B동)"),
        Data(R.drawable.list_icon, "공학A(아산공학관)"),
        Data(R.drawable.list_icon, "공학B(신공학관)"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = EwhaLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.list1.adapter = CustomAdapter(this, Data1List)
        binding.list2.adapter = CustomAdapter(this, Data2List)
        binding.list3.adapter = CustomAdapter(this, Data3List)
        binding.list4.adapter = CustomAdapter(this, Data4List)
        binding.list5.adapter = CustomAdapter(this, Data5List)
        binding.list6.adapter = CustomAdapter(this, Data6List)
    }

    override fun onDestroy() {
        vBinding = null
        super.onDestroy()
    }

}