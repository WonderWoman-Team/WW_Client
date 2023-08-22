package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.FragmentDeliveryBinding
import com.example.wonderwoman.databinding.SookmyungLocationBinding
import com.example.wonderwoman.delivery.DeliveryFragment
import com.example.wonderwoman.model.delivery.GetBuilding
import com.example.wonderwoman.util.GetAccessToken

class SookmyungActivity : AppCompatActivity(), GetBuilding {

    val TAG: String = "로그"

    private var vBinding : SookmyungLocationBinding? = null
    private val binding get() = vBinding!!
    private var selected = "전체"
    private lateinit var deliveryFragment: DeliveryFragment
    private lateinit var deliveryBinding: FragmentDeliveryBinding

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
        deliveryBinding = FragmentDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.list1.adapter = CustomAdapter(this, Data1List,this)
        binding.list2.adapter = CustomAdapter(this, Data2List,this)
        binding.list3.adapter = CustomAdapter(this, Data3List,this)
        binding.list4.adapter = CustomAdapter(this, Data4List,this)
        binding.list5.adapter = CustomAdapter(this, Data5List,this)
    }

    override fun onDestroy() {
        vBinding = null
        super.onDestroy()
    }

    override fun getBuilding(building: String){
        selected = building
//        val bundle: Bundle = Bundle()
//        bundle.putString("selected",selected)
//
        deliveryFragment = DeliveryFragment.newInstance()
        deliveryFragment.getSelectedBuilding(selected)
//        deliveryFragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, deliveryFragment).addToBackStack(null)
            .commit()
//        deliveryBinding.buildingName.text = selected
        Log.d("name", deliveryBinding.buildingName.text.toString())

    }
    fun getBuilding(): String{
        return selected
    }

}