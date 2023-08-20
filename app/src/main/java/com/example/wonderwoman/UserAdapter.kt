package com.example.wonderwoman

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context: Context, private val userList: List<list>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    //화면설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).
        inflate(R.layout.activity_userlist_item_gui,parent,false)

        return UserViewHolder(view)
    }

    //데이터설정
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        //데이터 담기
        val currentUser = userList[position]

        //화면에 데이터 보여주기
        holder.nameText.text = currentUser.userNickName
        holder.placeText.text=currentUser.building.toString()

        //아이템 클릭 이벤트
        holder.itemView.setOnClickListener{
            val intent= Intent(context,ChatActivity::class.java)

            //넘길데이터
            intent.putExtra("name",currentUser.userNickName)
            intent.putExtra("uId",currentUser.id)
            intent.putExtra("place",currentUser.building.toString())
            intent.putExtra("size",currentUser.sanitarySize)
            intent.putExtra("num",currentUser.sanitaryNum.toString())
            intent.putExtra("date",currentUser.createdAt)


            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameText: TextView = itemView.findViewById(R.id.name_listview_item)
        val placeText: TextView=itemView.findViewById(R.id.place_listview_item)

    }
}