package com.example.wonderwoman

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RoomStatusAdapter(private val context: Context, private val userList: List<DataRoomStatus?>):
RecyclerView.Adapter<RoomStatusAdapter.UserViewHolder>(){

    //화면설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).
        inflate(R.layout.activity_chat_gui,parent,false)

        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]

        val intent= Intent(context,ChatActivity::class.java)

        //넘길데이터
        intent.putExtra("roomId",currentUser?.id)
        intent.putExtra("status2",currentUser?.postStatus)

        //아이템 클릭 이벤트
        holder.itemView.setOnClickListener{



            context.startActivity(intent)
        }
    }



    override fun getItemCount(): Int {
        return userList.size
    }
    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    }
}

