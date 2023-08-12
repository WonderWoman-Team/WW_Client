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
import com.example.wonderwoman.databinding.TotalTabBinding
import com.example.wonderwoman.model.RetrofitClass
import com.example.wonderwoman.model.delivery.ResponseDelivery
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class TotalTab() : Fragment() {
    private lateinit var totalTabBinding: TotalTabBinding
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
        fun newInstance(): TotalTab {
            return TotalTab()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        totalTabBinding = TotalTabBinding.inflate(inflater, container, false)

        liner_btn = totalTabBinding.linerBtn
        small_btn = totalTabBinding.smallBtn
        middle_btn = totalTabBinding.middleBtn
        large_btn = totalTabBinding.largeBtn
        overnight_btn = totalTabBinding.overnightBtn

        recyclerView = totalTabBinding.postRecyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        fetchDelivery(sizeList)

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

        return totalTabBinding.root
    }

    override fun onResume() {
        super.onResume()
        liner_btn.isChecked = false
        small_btn.isChecked = false
        middle_btn.isChecked = false
        large_btn.isChecked = false
        overnight_btn.isChecked = false
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

    fun setRecyclerAdapter(deliveryList: List<ResponseDelivery.Delivery>) {
        recyclerAdapter = PostRecyclerAdapter(deliveryList, requireContext())
        recyclerView.adapter = recyclerAdapter
        recyclerAdapter.notifyDataSetChanged()
        recyclerView.setHasFixedSize(true)
    }
}