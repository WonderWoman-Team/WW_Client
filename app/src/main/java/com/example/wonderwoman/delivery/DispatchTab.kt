package com.example.wonderwoman.delivery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wonderwoman.R
import com.example.wonderwoman.databinding.DispatchTabBinding
import com.example.wonderwoman.model.RetrofitClass
import com.example.wonderwoman.model.delivery.ResponseDelivery
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class DispatchTab() : Fragment() {
    private lateinit var dispatchTabBinding: DispatchTabBinding
    private lateinit var liner_btn: CheckBox
    private lateinit var small_btn: CheckBox
    private lateinit var middle_btn: CheckBox
    private lateinit var large_btn: CheckBox
    private lateinit var overnight_btn: CheckBox
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: PostRecyclerAdapter
    var data: ResponseDelivery? = null
    var deliveryList: List<ResponseDelivery.Delivery>? = null
    var sizeList: MutableList<String> = mutableListOf()

    companion object {
        fun newInstance(): DispatchTab {
            return DispatchTab()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dispatchTabBinding = DispatchTabBinding.inflate(inflater, container, false)

        this.liner_btn = dispatchTabBinding.linerBtn
        small_btn = dispatchTabBinding.smallBtn
        middle_btn = dispatchTabBinding.middleBtn
        large_btn = dispatchTabBinding.largeBtn
        overnight_btn = dispatchTabBinding.overnightBtn

        recyclerView = dispatchTabBinding.postRecyclerview //리사이클러뷰 연결
        recyclerView.setHasFixedSize(true) //recyclerview 성능 강화
        recyclerView.layoutManager = LinearLayoutManager(activity)

        //버튼 이벤트
        var listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            when (buttonView.id) {
                R.id.liner_btn -> {
                    if (isChecked) sizeList.add("라이너") else sizeList.remove("라이너")
                }

                R.id.small_btn -> {
                    if (isChecked) sizeList.add("소형") else sizeList.remove("소형")
                }

                R.id.middle_btn -> {
                    if (isChecked) sizeList.add("중형") else sizeList.remove("중형")
                }

                R.id.large_btn -> {
                    if (isChecked) sizeList.add("대형") else sizeList.remove("대형")
                }

                R.id.overnight_btn -> {
                    if (isChecked) sizeList.add("오버나이트") else sizeList.remove("오버나이트")
                }
            }
            if (!liner_btn.isChecked && !small_btn.isChecked && !middle_btn.isChecked && !large_btn.isChecked && !overnight_btn.isChecked) {
                sizeList.clear()
            }
            fetchDelivery(sizeList)?.let { setRecyclerAdapter(it) }
                ?: setRecyclerAdapter(emptyList())
        }
        liner_btn.setOnCheckedChangeListener(listener)
        small_btn.setOnCheckedChangeListener(listener)
        middle_btn.setOnCheckedChangeListener(listener)
        large_btn.setOnCheckedChangeListener(listener)
        overnight_btn.setOnCheckedChangeListener(listener)
        return dispatchTabBinding.root
    }

    private fun fetchDelivery(size: List<String>): List<ResponseDelivery.Delivery>? {
        val callDelivery: Call<ResponseDelivery> =
            RetrofitClass.deliveryAPI.getDeliveryList(null, null, size, null)
        callDelivery.enqueue(object : retrofit2.Callback<ResponseDelivery> {
            override fun onResponse(
                call: Call<ResponseDelivery>,
                response: Response<ResponseDelivery>
            ) {
                response.takeIf { it.isSuccessful }?.body()?.let { it ->
                    data = response.body()
                    Log.d("success", data.toString())
                    deliveryList = it.content
                } ?: showError(response.errorBody())
            }

            override fun onFailure(call: Call<ResponseDelivery>, t: Throwable) {
                //통신 실패 로직
                t.message?.let { Log.d("fail", it) }
            }
        })
        return deliveryList
    }

    fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
        Log.d("error", ob.getString("message"))
    }

    private fun setRecyclerAdapter(deliveryList: List<ResponseDelivery.Delivery>) {
        recyclerAdapter = PostRecyclerAdapter(deliveryList, requireContext())
        recyclerView.adapter = recyclerAdapter
        recyclerAdapter.notifyDataSetChanged()
        recyclerView.setHasFixedSize(true)
    }
}