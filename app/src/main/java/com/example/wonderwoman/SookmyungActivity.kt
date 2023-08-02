package com.example.wonderwoman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.SookmyungLocationBinding

class SookmyungActivity : AppCompatActivity() {

    val TAG: String = "로그"

    private var vBinding : SookmyungLocationBinding? = null
    private val binding get() = vBinding!!

    var Data1List = arrayListOf(
        Data(R.drawable.list_icon, "전체"))
    var Data2List = arrayListOf(
        Data(R.drawable.list_icon, "순헌관"),
        Data(R.drawable.list_icon, "명신관"),
        Data(R.drawable.list_icon, "학생회관"),
        Data(R.drawable.list_icon, "프라임관"),
        Data(R.drawable.list_icon, "중앙도서관"))
    var Data3List = arrayListOf(
        Data(R.drawable.list_icon, "진리관"),
        Data(R.drawable.list_icon, "새힘관"),
        Data(R.drawable.list_icon, "행정관"))
    var Data4List = arrayListOf(
        Data(R.drawable.list_icon, "르네상스플라자"),
        Data(R.drawable.list_icon, "음악대학"),
        Data(R.drawable.list_icon, "사회교육관"),
        Data(R.drawable.list_icon, "약학대학"),
        Data(R.drawable.list_icon, "미술대학"))
    var Data5List = arrayListOf(
        Data(R.drawable.list_icon, "백주년기념관"),
        Data(R.drawable.list_icon, "과학관"),
        Data(R.drawable.list_icon, "다목적관"),
        Data(R.drawable.list_icon, "새빛관"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = SookmyungLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.list1.adapter = CustomAdapter(this, Data1List)
        binding.list2.adapter = CustomAdapter(this, Data2List)
        binding.list3.adapter = CustomAdapter(this, Data3List)
        binding.list4.adapter = CustomAdapter(this, Data4List)
        binding.list5.adapter = CustomAdapter(this, Data5List)
    }

    override fun onDestroy() {
        vBinding = null
        super.onDestroy()
    }

}