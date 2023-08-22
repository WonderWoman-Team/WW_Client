package com.example.wonderwoman.delivery

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.wonderwoman.ChatActivity
import com.example.wonderwoman.MainActivity
import com.example.wonderwoman.R
import com.example.wonderwoman.UserList
import com.example.wonderwoman.model.RetrofitClass
import com.example.wonderwoman.model.delivery.ResponseDelivery.Delivery
import com.example.wonderwoman.model.delivery.ResponseRemoveDelivery
import com.example.wonderwoman.util.Constants
import com.example.wonderwoman.util.CustomToast
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


class PostRecyclerAdapter(
    private val deliveryList: List<Delivery>,
    val context: Context,
    val destination: String
) :
    RecyclerView.Adapter<PostRecyclerAdapter.CustomViewHolder>() {
    var mainActivity: MainActivity? = null
    var status: String? = ""
    var response: String? = ""
    var cnt = 0
    private lateinit var writePost: Button

    //recyclerView가 Adapter에 연결된 후 최초로 실행되는 부분.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //뷰홀더에 전달
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list, parent, false)
        mainActivity = context as MainActivity
        return CustomViewHolder(view)
    }

    //각 아이템들에 대한 매칭이 이뤄지는 곳
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.location.text = deliveryList[position].postTitle
        holder.size.text = deliveryList[position].sanitarySize
        holder.count.text = deliveryList[position].sanitaryNum.toString()
        holder.type.text = deliveryList[position].sanitaryType
        holder.time.text = deliveryList[position].createdAt
        holder.nickname.text = deliveryList[position].nickname
        holder.post_state.text = "${deliveryList[position].postReqType}글"
        holder.chat_state.text = deliveryList[position].postStatus

        Log.d(
            "log",
            "$cnt + ${holder.location.text} + ${holder.size.text} + ${holder.count.text} + ${holder.type.text} + ${holder.time.text} + ${holder.nickname.text} + ${holder.post_state.text} + ${holder.chat_state.text}"
        )
        //본인이 작성한 게시글인 경우
        if (deliveryList[position].written) {
            holder.go_to_chat_btn.setBackgroundResource(R.drawable.cancel_delivery_btn)
            holder.go_to_chat_btn.setTextColor(Color.rgb(249, 57, 95))
            if (holder.post_state.text.equals("요청글")) {
                holder.go_to_chat_btn.text = "요청취소   x"
            } else if (holder.post_state.text.equals("출동글")) {
                holder.go_to_chat_btn.text = "출동취소   x"
            }

        } else { //본인이 작성한 게시글이 아니라 다른 사람들의 게시글인 경우
            holder.go_to_chat_btn.setBackgroundResource(R.drawable.go_out_btn)
            holder.go_to_chat_btn.setTextColor(Color.WHITE)
            if (holder.post_state.text.equals("요청글")) {
                holder.go_to_chat_btn.text = "출동하기   →"
            } else if (holder.post_state.text.equals("출동글")) {
                holder.go_to_chat_btn.text = "요청하기   →"
            }
        }
        //진행 상태가 없음 일 경우
        if (holder.chat_state.text.equals("없음")) {
//            holder.chat_state.text = ""
            holder.chat_state_img.setBackgroundResource(0)
            holder.post_state.setTextColor(Color.parseColor("#F9395F"))
            holder.chat_state.setTextColor(Color.parseColor("#FFFFFF"))
            holder.post_state_img.setBackgroundResource(R.drawable.connect)
            holder.go_to_chat_btn.isEnabled = true
        } else if (holder.chat_state.text.equals("완료") || holder.chat_state.text.equals("취소")) {
            if (holder.chat_state.text.equals("완료")) {
                holder.chat_state_img.setBackgroundResource(R.drawable.complete)
            } else {
                holder.chat_state_img.setBackgroundResource(R.drawable.cancel)
            }
            holder.post_state_img.setBackgroundResource(R.drawable.complete_delivery)
            holder.chat_state.setTextColor(Color.parseColor("#CCCCCC"))
            holder.post_state.setTextColor(Color.parseColor("#CCCCCC"))
            holder.go_to_chat_btn.setBackgroundResource(R.drawable.complete_go_out_btn)
            holder.go_to_chat_btn.setTextColor(Color.WHITE)
            holder.go_to_chat_btn.isEnabled = false
        } else if (holder.chat_state.text.equals("채팅중") || holder.chat_state.text.equals("진행중")) {
            holder.post_state_img.setBackgroundResource(R.drawable.connect)
            holder.chat_state_img.setBackgroundResource(R.drawable.loader)
            holder.post_state.setTextColor(Color.parseColor("#F9395F"))
            holder.chat_state.setTextColor(Color.parseColor("#F9395F"))
            holder.go_to_chat_btn.isEnabled = true
        }

        //리스트 클릭 이벤트
        //api로 연결하면 로직 변경 예
        holder.go_to_chat_btn.setOnClickListener {
            if (holder.go_to_chat_btn.text.contains("취소")) {
                var id = deliveryList[position].id
                response = removePost(id, position, holder.chat_state.text as String, it)
                Log.d("chat", "${holder.chat_state.text}")

            } else if (holder.go_to_chat_btn.text.contains("하기")) {
//                mainActivity!!.changeFragment(3)
val intent = Intent(context, ChatActivity::class.java)
                //넘길데이터
                intent.putExtra("name",holder.nickname.text)
                intent.putExtra("uId", deliveryList[position].id.toString())
                intent.putExtra("place",holder.location.text)
                intent.putExtra("size",holder.size.text)
                intent.putExtra("num",holder.count.text)
                intent.putExtra("date",holder.time.text)
                intent.putExtra("status",holder.chat_state.text)

                context.startActivity(intent)
//                val fragment = UserList.newInstance()
//
//                val fragmentManager: FragmentManager =
//                    (it.context as FragmentActivity).supportFragmentManager // instantiate your view context
//
//                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//                fragmentTransaction.replace(
//                    R.id.fragment,
//                    fragment
//                ) // your container and your fragment
//
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                fragmentTransaction.addToBackStack(null)
//                fragmentTransaction.commit()


                Log.d("press", "press")
            }
        }
    }


    override fun getItemCount(): Int {
        return deliveryList.size
    }

    //리사이클러뷰로 구현한 아이템을 불러오는 부분
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var location = itemView.findViewById<TextView>(R.id.post_location)
        var size = itemView.findViewById<TextView>(R.id.post_size)
        var count = itemView.findViewById<TextView>(R.id.post_count)
        var type = itemView.findViewById<TextView>(R.id.post_type)
        var time = itemView.findViewById<TextView>(R.id.post_time)
        var nickname = itemView.findViewById<TextView>(R.id.post_writer_nickname)
        var chat_state = itemView.findViewById<TextView>(R.id.post_chat_state)
        var post_state = itemView.findViewById<TextView>(R.id.post_state)
        var chat_state_img = itemView.findViewById<ImageView>(R.id.post_chat_state_img)
        var post_state_img = itemView.findViewById<ImageView>(R.id.post_state_img)
        var go_to_chat_btn = itemView.findViewById<Button>(R.id.go_to_chat_btn)
    }

    private fun removePost(path: Int, position: Int, state: String, it: View): String? {
        val callRemoveDelivery: Call<ResponseRemoveDelivery> =
            RetrofitClass.deliveryAPI.deleteDeliveryPost(Constants.ACCESS_TOKEN, path)
        Log.d("fetchRemove", "${path} + $state")
        callRemoveDelivery.enqueue(object : retrofit2.Callback<ResponseRemoveDelivery> {
            override fun onResponse(
                call: Call<ResponseRemoveDelivery>,
                response: Response<ResponseRemoveDelivery>
            ) {
                if (response.isSuccessful) {
                    val result: Response<ResponseRemoveDelivery> = response
                    Log.d(
                        "success",
                        "remove post + ${result.code()} + ${result.body()} + ${result.raw()}"
                    )
                    status = result.body()?.status
                    Log.d("status", status.toString())
                    CustomToast.showToast(context, "성공적으로 취소되었습니다")
                    var fragment: Fragment = DeliveryFragment.newInstance()

                    val fragmentManager: FragmentManager =
                        (it.context as FragmentActivity).supportFragmentManager // instantiate your view context


                    val fragmentTransaction: FragmentTransaction =
                        fragmentManager.beginTransaction()
                    fragmentTransaction.replace(
                        R.id.fragment,
                        fragment
                    ) // your container and your fragment

                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                } else {
                    showError(response.errorBody())
                }
            }

            override fun onFailure(call: Call<ResponseRemoveDelivery>, t: Throwable) {
                //통신 실패 로직
                t.message?.let { Log.d("fail remove", t.message.toString()) }
            }
        })
        return status
    }

    fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
        Log.d("fetchRemove", "${ob.getString("solution")}")

        CustomToast.showToast(context, "본인이 작성한 게시글만 삭제할 수 있습니다.")
    }
}