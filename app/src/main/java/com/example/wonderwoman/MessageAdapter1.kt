package com.example.wonderwoman

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter1(private val context: Context, private val messageList: ArrayList<com.example.wonderwoman.Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){



    private val receive=1
    private val send=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==1){//받는 화면
            val view: View=LayoutInflater.from(context).inflate(R.layout.activity_receive,parent,false)
            ReceiveViewHolder(view)
        }else{//보내는 화면
            val view: View=LayoutInflater.from(context).inflate(R.layout.activity_send,parent,false)
            SendViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //현재 메세지
        val currentMessage = messageList[position]

        //보내는 데이터
        if(holder.javaClass==SendViewHolder::class.java){
            val viewHolder = holder as SendViewHolder
            viewHolder.sendMessage.text=currentMessage.message
        }else{//받는 데이터
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.receiveMessage.text=currentMessage.message

        }
        //넘길데이터
        val intent2= Intent(context,ChatActivity::class.java)
        intent2.putExtra("roomId",currentMessage.roomId)
    }
    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        //메세지 값
        val currentMessage = messageList[position]



        val preferences:SharedPreferences = context.getSharedPreferences("ChatRoomId", Context.MODE_PRIVATE)
        Log.d("1","${currentMessage.senderNickname}")
        Log.d("2","${preferences.getString("nickName",null)}")


        return if(currentMessage.senderNickname==preferences.getString("nickName",null)){
            send
        }else{
            receive
        }
    }


    //보낸쪽
    class SendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sendMessage: TextView= itemView.findViewById(R.id.send_message_text)
    }
    //받는쪽
    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val receiveMessage: TextView=itemView.findViewById(R.id.receive_message_text)
    }



    fun addMessage(message:Message){
        messageList.add(message)
        notifyDataSetChanged()
    }
}
