package com.example.wonderwoman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
        holder.chat_state.text = PostList.get(position).chat_state
        holder.post_state.text = PostList.get(position).post_state
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
    }
}