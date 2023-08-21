package com.example.wonderwoman

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter3(private val context: Context, private val messageList: ArrayList<com.example.wonderwoman.Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val receive=1
    private val send=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View=LayoutInflater.from(context).inflate(R.layout.activity_userlist_item_gui,parent,false)
        return SendViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //현재 메세지
        val currentMessage = messageList[position]

        val viewHolder = holder as SendViewHolder
        viewHolder.sendMessage.text=currentMessage.message

        val intent2= Intent(context,ChatActivity::class.java)

        //넘길데이터
        intent2.putExtra("roomId",currentMessage.roomId)
    }
    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        //메세지 값
        val currentMessage = messageList[position]

        return send
    }


    //보낸쪽
    class SendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sendMessage: TextView= itemView.findViewById(R.id.message_userlist_item)
    }
    //받는쪽
    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val receiveMessage: TextView=itemView.findViewById(R.id.receive_message_text)
    }


    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.send_message_text)

        fun bind(message: Message) {
            messageTextView.text = message.toString()
        }
    }
    fun addMessage(message:Message){
        messageList.add(message)
        notifyDataSetChanged()
    }
}
