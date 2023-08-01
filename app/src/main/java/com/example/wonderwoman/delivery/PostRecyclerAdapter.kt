package com.example.wonderwoman.delivery

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wonderwoman.R

class PostRecyclerAdapter(val PostList: ArrayList<Post>): RecyclerView.Adapter<PostRecyclerAdapter.CustomViewHolder>() {

    //recyclerView가 Adapter에 연결된 후 최초로 실행되는 부분.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //뷰홀더에 전달

        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list,parent,false)
        return CustomViewHolder(view)
    }

    //각 아이템들에 대한 매칭이 이뤄지는 곳
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.location.text = PostList.get(position).location
        holder.size.text = PostList.get(position).size
        holder.count.text = PostList.get(position).count.toString()
        holder.type.text = PostList.get(position).type
        holder.time.text = PostList.get(position).time
        holder.nickname.text = PostList.get(position).nickname
        holder.post_state.text = PostList.get(position).post_state
        if(holder.nickname.text.equals("본인")){
            holder.go_to_chat_btn.setBackgroundResource(R.drawable.cancel_delivery_btn)
            holder.go_to_chat_btn.setTextColor(Color.rgb(249,57,95))
            holder.go_to_chat_btn.setPadding(30,0,0,0)
            if(holder.post_state.text.equals("요청글")) {
                holder.go_to_chat_btn.text = "요청취소   x"
            }
            else if(holder.post_state.text.equals("출동글")){
                holder.go_to_chat_btn.text = "출동취소   x"
            }
        }
        else{ //본인이 작성한 게시글이 아니라 다른 사람들의 게시글인 경우
            holder.chat_state.text = PostList.get(position).chat_state

            if(holder.post_state.text.equals("요청글")) {
                holder.go_to_chat_btn.text = "요청하기 "
            }
            else if(holder.post_state.text.equals("출동글")){
                holder.go_to_chat_btn.text = "출동하기 "
            }

            if(holder.chat_state.text.equals("완료")){
                holder.chat_state_img.setBackgroundResource(R.drawable.complete)
                holder.post_state_img.setBackgroundResource(R.drawable.complete_delivery)
                holder.chat_state.setTextColor(Color.parseColor("#CCCCCC"))
                holder.post_state.setTextColor(Color.parseColor("#CCCCCC"))
                holder.go_to_chat_btn.setBackgroundResource(R.drawable.complete_go_out_btn)
                holder.go_to_chat_btn.setPadding(20,0,30,0)
            }
            else if(holder.chat_state.text.equals("채팅중") || holder.chat_state.text.equals("진행중")){
                holder.chat_state_img.setBackgroundResource(R.drawable.loader)
            }
        }

        //리스트 클릭 이벤트
        holder.go_to_chat_btn.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return PostList.size
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
}