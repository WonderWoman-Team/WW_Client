package com.example.wonderwoman


import android.os.Bundle
import android.os.Binder
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wonderwoman.databinding.ActivityChatBinding
import com.example.wonderwoman.databinding.ActivityChatGuiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.w3c.dom.Text

class ChatActivity : AppCompatActivity() {
    private lateinit var receiverName: String
    private lateinit var receiverUid: String

    //바인딩 객체
    private lateinit var binding: ActivityChatGuiBinding

    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef: DatabaseReference

    private lateinit var receiverRoom:String//받는 대화방
    private lateinit var senderRoom: String//보내는 대화방

    private lateinit var messageList: ArrayList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatGuiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //초기화
        messageList=ArrayList()
        val messageAdapter: MessageAdapter = MessageAdapter(this, messageList)

        //RecyclerView
        binding.chatRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerview.adapter=messageAdapter

        //넘어온 데이터 변수에 담기
        receiverName=intent.getStringExtra("name").toString()
        receiverUid=intent.getStringExtra("uId").toString()

        mAuth=FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().reference

        //접속자 Uid
        val senderUid=mAuth.currentUser?.uid

        //보낸이 방
        senderRoom=receiverUid+senderUid

        //받는이 방
        receiverRoom=senderUid+receiverUid

        //액션바에 상대방 이름 보여주기
        //supportActionBar?.title=receiverName


        val supportaction = findViewById<TextView>(R.id.receive_name_id)
        supportaction.text=receiverName

        val supportaction2= findViewById<TextView>(R.id.receive_name_text)
        supportaction2.text=receiverName


        //메세지전송
        binding.sendBtn.setOnClickListener{
            val message = binding.messageEdit.text.toString()
            val messageObject=Message(message,senderUid)

            //데이터 저장
            mDbRef.child("chats").child(senderRoom).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    //저장 성공하면
                    mDbRef.child("chats").child(receiverRoom).child("messages").push()
                        .setValue(messageObject)
                }
            //입력 부분 초기화
            binding.messageEdit.setText("")
        }
        //메세지 가져오기
        mDbRef.child("chats").child(senderRoom).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for(postSnapshat in snapshot.children){
                        val message=postSnapshat.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    //적용 화면에 보이게 하기
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
}