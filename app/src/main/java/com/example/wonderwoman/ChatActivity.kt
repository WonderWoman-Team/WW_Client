package com.example.wonderwoman


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wonderwoman.databinding.ActivityChatGuiBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URI

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

    lateinit var adapter: UserAdapter
    lateinit var adapter2: RoomStatusAdapter

    //웹소켓
    private lateinit var messageText: TextView
    private lateinit var messageEdit: EditText
    private lateinit var sendButton: Button
    private lateinit var webSocketClient: WebSocketClient
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter1
    //메뉴 상태
    lateinit var roomStatusApi: ArrayList<DataRoomStatus>
    var request: ChatRoomRequest? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatGuiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dot3()

        //status
        val chatRoomId = intent.getStringExtra("uId").toString()
        val savedPostStatus = getPostStatus(this@ChatActivity, chatRoomId)

        if (savedPostStatus == "채팅중") {
            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_chatting_chatroom)
        } else if (savedPostStatus == "진행중") {
            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_delivery_chatroom)
        }else if (savedPostStatus == "완료") {
            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_finish_chatroom)

        }


        //뒤로가기
        binding.backToUserlist.setOnClickListener{
//            val intent=Intent(this,MainActivity::class.java)
//            startActivity(intent)
            onBackPressed()
        }



        //채팅방 id넘기기
        val sharedPreferences2 = getSharedPreferences("ChatRoomId", Context.MODE_PRIVATE)
        val editor2 = sharedPreferences2.edit()
        editor2.putString("nickName", intent.getStringExtra("name").toString())
        editor2.apply()

//        //초기화
//        messageList=ArrayList()
//        val messageAdapter: MessageAdapter = MessageAdapter(this, messageList)
//
//        //RecyclerView
//        binding.chatRecyclerview.layoutManager = LinearLayoutManager(this)
//        binding.chatRecyclerview.adapter=messageAdapter
//
        //넘어온 데이터 변수에 담기
        receiverName=intent.getStringExtra("name").toString()
        receiverUid=intent.getStringExtra("uId").toString()
//
//        mAuth=FirebaseAuth.getInstance()
//        mDbRef=FirebaseDatabase.getInstance().reference
//
//        //접속자 Uid
//        val senderUid=mAuth.currentUser?.uid
//
//        //보낸이 방
//        senderRoom=receiverUid+senderUid
//
//        //받는이 방
//        receiverRoom=senderUid+receiverUid
//
//
//
        val supportaction = findViewById<TextView>(R.id.receive_name_id)
        supportaction.text=receiverName

        val supportaction2= findViewById<TextView>(R.id.receive_name_text)
        supportaction2.text=receiverName

        findViewById<TextView>(R.id.place_chat).text=intent.getStringExtra("place").toString()
        findViewById<TextView>(R.id.size_chat).text=intent.getStringExtra("size").toString()
        findViewById<TextView>(R.id.num_chat).text= intent.getStringExtra("num").toString()
        findViewById<TextView>(R.id.date_chat).text=intent.getStringExtra("date").toString()

//        //메세지전송
//        binding.sendBtn.setOnClickListener{
//            val message = binding.messageEdit.text.toString()
//            val messageObject=Message(message,senderUid)
//
//            //데이터 저장
//            mDbRef.child("chats").child(senderRoom).child("messages").push()
//                .setValue(messageObject).addOnSuccessListener {
//                    //저장 성공하면
//                    mDbRef.child("chats").child(receiverRoom).child("messages").push()
//                        .setValue(messageObject)
//                }
//            //입력 부분 초기화
//            binding.messageEdit.setText("")
//        }
//        //메세지 가져오기
//        mDbRef.child("chats").child(senderRoom).child("messages")
//            .addValueEventListener(object : ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    messageList.clear()
//
//                    for(postSnapshat in snapshot.children){
//                        val message=postSnapshat.getValue(Message::class.java)
//                        messageList.add(message!!)
//                    }
//                    //적용 화면에 보이게 하기
//                    messageAdapter.notifyDataSetChanged()
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//
//       }

        ////////////

        /////////웹소켓
        messageText = layoutInflater.inflate(R.layout.activity_send, null).findViewById(R.id.send_message_text)
        messageEdit = binding.messageEdit
        sendButton = binding.sendBtn
        recyclerView = binding.chatRecyclerview

        messageList=ArrayList()
        messageAdapter = MessageAdapter1(this, messageList) // 어댑터 초기화

        recyclerView.adapter = messageAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        receiverName=intent.getStringExtra("name").toString()
        receiverUid=intent.getStringExtra("uId").toString()

        val senderUid=intent.getStringExtra("senderId").toString()

        //보낸이 방
        senderRoom=receiverUid+senderUid

        //받는이 방
        receiverRoom=senderUid+receiverUid


        sendButton.setOnClickListener {
            val message = messageEdit.text.toString()
            if (message.isNotEmpty()) {
                if (webSocketClient.isOpen) {
                    sendMessage(message,intent.getStringExtra("uId").toString())
                    messageEdit.text.clear()
                    Log.d("WebSocketDebug", "Message sent: $message")
                } else {
                    // WebSocket 연결이 열려 있지 않을 때 처리
                }
            }
        }


        val serverUri = URI("ws://43.202.116.247:8080/ws/chat")


        webSocketClient = object : WebSocketClient(serverUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d("WebSocketDebug", "열림")
                // 연결이 열렸을 때 처리할 내용
            }

            override fun onMessage(message: String?) {
                runOnUiThread {
                    message?.let {
                        Log.d("WebSocketDebug1", "Message sent: " + message)
                        displayMessage(it,intent.getStringExtra("uId").toString())
                    }
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                // 연결이 닫혔을 때 처리할 내용
                Log.e("WebSocketError", "닫힘")
            }

            override fun onError(ex: Exception?) {
                // 에러 처리
                ex?.printStackTrace() // 에러 스택 트레이스 출력
                Log.e("WebSocketError", "WebSocket error: ${ex?.message}")
            }
        }

        webSocketClient.connect()
        // 이전 메시지 로드

        loadPreviousMessages(intent.getStringExtra("uId").toString())
    }
    private fun loadPreviousMessages(roomId: String) {
        val sharedPreferences = getSharedPreferences("ChatHistory", Context.MODE_PRIVATE)
        val messagesJson = sharedPreferences.getString(roomId, null)

        messagesJson?.let {
            val type = object : TypeToken<ArrayList<Message>>() {}.type
            val savedMessages = Gson().fromJson<ArrayList<Message>>(it, type)
            messageList.addAll(savedMessages)
            messageAdapter.notifyDataSetChanged()
        }


    }
    ///
    private fun sendMessage(message: String, roomId: String) {
        webSocketClient.send(message)
        webSocketClient.onMessage(message)
    }
    private fun displayMessage(message: String, roomId: String) {
        val messageLayout = layoutInflater.inflate(R.layout.activity_send, null)
        messageLayout.findViewById<TextView>(R.id.send_message_text).text = message
        val messageObject = Message("TALK", intent.getStringExtra("name").toString(),message, roomId)
        Log.d("roomId", roomId)
        messageAdapter.addMessage(messageObject)
        messageAdapter.notifyDataSetChanged()

        // Save the new message with the roomId
        saveMessage(messageObject, roomId)
    }

    private fun saveMessage(message: Message, roomId: String) {
        val sharedPreferences = getSharedPreferences("ChatHistory", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Fetch the existing messages for the current roomId
        val existingMessagesJson = sharedPreferences.getString(roomId, null)
        val existingType = object : TypeToken<ArrayList<Message>>() {}.type
        val existingMessages = Gson().fromJson<ArrayList<Message>>(existingMessagesJson, existingType) ?: ArrayList()

        // Add the new message to the existing messages for the current roomId
        existingMessages.add(message)

        // Convert the updated messages to JSON and save them with the roomId as the key
        val updatedMessagesJson = Gson().toJson(existingMessages)
        editor.putString(roomId, updatedMessagesJson)
        editor.apply()


    }

    ///



    fun dot3(){
        //점3개 상태변경탭
        val bottomSheetView = layoutInflater.inflate(R.layout.activity_chat_status_menu, null)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)

        findViewById<ImageButton>(R.id.chat_status_dot3).setOnClickListener {
            bottomSheetDialog.show()

        }
//        val userlistview = LayoutInflater.from(this).inflate(R.layout.activity_userlist_item_gui, null)
        bottomSheetView.findViewById<Button>(R.id.status_chatting).setOnClickListener {
            bottomSheetDialog.dismiss() // 버튼을 클릭하면 Bottom Sheet를 닫습니다.
//            userlistview.findViewById<ImageView>(R.id.status_chatting_userlist_item).setImageResource(R.drawable.ic_status_chatting)
            apiChatting()
            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_chatting_chatroom)
        }

        bottomSheetView.findViewById<Button>(R.id.status_delivery).setOnClickListener {
            bottomSheetDialog.dismiss() // 버튼을 클릭하면 Bottom Sheet를 닫습니다.
//            userlistview.findViewById<ImageView>(R.id.status_chatting_userlist_item).setImageResource(R.drawable.ic_status_delivery)
            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_delivery_chatroom)
            apiDelivery()
        }

        bottomSheetView.findViewById<Button>(R.id.status_finish).setOnClickListener {
            bottomSheetDialog.dismiss() // 버튼을 클릭하면 Bottom Sheet를 닫습니다.
//            userlistview.findViewById<ImageView>(R.id.status_chatting_userlist_item).setImageResource(R.drawable.ic_status_finish)
            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_finish_chatroom)
            apiFinish()
        }



    }

    fun apiDelivery(){
        request = ChatRoomRequest(
            chatRoomId = "${intent.getStringExtra("uId").toString()}",
            status = "진행중"
        )

        request?.let {
            RetrofitClassChat.apiRoomStatus.getRoomStatus(
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJlbWFpbCI6InN5ZW9uMjVAbWp1LmFjLmtyIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTY5MTA5Mzk2MCwiZXhwIjoxNjkxMTM3MTYwfQ.plPlQZNyhV1PsDMKpf1Wj7QULDoP_9LbBGUBUiT7u-yRYITEGSB9xFHMAWyxHiKUSs9mmeMUu_zZdjhEEcjIUg",
                "refresh-token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZWZyZXNoLXRva2VuIiwiaWF0IjoxNjkxMDkzOTYwLCJleHAiOjE2OTIzMDM1NjB9._4nK_LoXEM7px_kWZuFYh6yHDnVLJsuY9OiXQcxpkkQv8GOuxk5yqcVOWIqEVch2AA4HWlZv7nbuLqhSQd6cVg",
                it
            ).enqueue(object: Callback<DataRoomStatus> {


                override fun onResponse(call: Call<DataRoomStatus>, response: Response<DataRoomStatus>) {
                    if (response.isSuccessful) {
                        val roomStatusApi = response.body()

                        Log.d("채팅방id","${response.body()?.id}")
                        Log.d("상태","${response.body()?.postStatus}")

//                        RoomStatusAdapter(this@ChatActivity, listOf(response.body())!!)
                        if(response.body()?.postStatus=="채팅중")
                            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_chatting_chatroom)
                        if(response.body()?.postStatus=="진행중")
                            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_delivery_chatroom)
                        if(response.body()?.postStatus=="완료")
                            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_finish_chatroom)

                        RoomStatusAdapter(this@ChatActivity, listOf(response.body()))
                        val intent= Intent(this@ChatActivity,UserList::class.java)
                        //넘길데이터
                        intent.putExtra("status2",response.body()?.postStatus)
                        val postStatus = response.body()?.postStatus

                        savePostStatus(this@ChatActivity, response.body()!!.id, postStatus!!)

                    } else {
                        Log.d("error", "Response not successful: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<DataRoomStatus>, t: Throwable) {
                    t.message?.let { Log.d("fail3", t.message.toString()) }
                }

            })
        }

    }
    fun apiChatting(){
        request = ChatRoomRequest(
            chatRoomId = "${intent.getStringExtra("uId").toString()}",
            status = "채팅중"
        )

        request?.let {
            RetrofitClassChat.apiRoomStatus.getRoomStatus(
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJlbWFpbCI6InN5ZW9uMjVAbWp1LmFjLmtyIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTY5MTA5Mzk2MCwiZXhwIjoxNjkxMTM3MTYwfQ.plPlQZNyhV1PsDMKpf1Wj7QULDoP_9LbBGUBUiT7u-yRYITEGSB9xFHMAWyxHiKUSs9mmeMUu_zZdjhEEcjIUg",
                "refresh-token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZWZyZXNoLXRva2VuIiwiaWF0IjoxNjkxMDkzOTYwLCJleHAiOjE2OTIzMDM1NjB9._4nK_LoXEM7px_kWZuFYh6yHDnVLJsuY9OiXQcxpkkQv8GOuxk5yqcVOWIqEVch2AA4HWlZv7nbuLqhSQd6cVg",
                it
            ).enqueue(object: Callback<DataRoomStatus> {


                override fun onResponse(call: Call<DataRoomStatus>, response: Response<DataRoomStatus>) {
                    if (response.isSuccessful) {
                        val roomStatusApi = response.body()

                        Log.d("채팅방id","${response.body()?.id}")
                        Log.d("상태","${response.body()?.postStatus}")

//                        RoomStatusAdapter(this@ChatActivity, listOf(response.body())!!)
                        if(response.body()?.postStatus=="채팅중")
                            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_chatting_chatroom)
                        if(response.body()?.postStatus=="진행중")
                            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_delivery_chatroom)
                        if(response.body()?.postStatus=="완료")
                            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_finish_chatroom)

                        val postStatus = response.body()?.postStatus
                        savePostStatus(this@ChatActivity, response.body()!!.id, postStatus!!)


                    } else {
                        Log.d("error", "Response not successful: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<DataRoomStatus>, t: Throwable) {
                    t.message?.let { Log.d("fail3", t.message.toString()) }
                }

            })
        }

    }
    fun apiFinish(){
        request = ChatRoomRequest(
            chatRoomId = "${intent.getStringExtra("uId").toString()}",
            status = "완료"
        )

        request?.let {
            RetrofitClassChat.apiRoomStatus.getRoomStatus(
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJlbWFpbCI6InN5ZW9uMjVAbWp1LmFjLmtyIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTY5MTA5Mzk2MCwiZXhwIjoxNjkxMTM3MTYwfQ.plPlQZNyhV1PsDMKpf1Wj7QULDoP_9LbBGUBUiT7u-yRYITEGSB9xFHMAWyxHiKUSs9mmeMUu_zZdjhEEcjIUg",
                "refresh-token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZWZyZXNoLXRva2VuIiwiaWF0IjoxNjkxMDkzOTYwLCJleHAiOjE2OTIzMDM1NjB9._4nK_LoXEM7px_kWZuFYh6yHDnVLJsuY9OiXQcxpkkQv8GOuxk5yqcVOWIqEVch2AA4HWlZv7nbuLqhSQd6cVg",
                it
            ).enqueue(object: Callback<DataRoomStatus> {


                override fun onResponse(call: Call<DataRoomStatus>, response: Response<DataRoomStatus>) {
                    if (response.isSuccessful) {
                        val roomStatusApi = response.body()

                        Log.d("채팅방id","${response.body()?.id}")
                        Log.d("상태","${response.body()?.postStatus}")

//                        RoomStatusAdapter(this@ChatActivity, listOf(response.body())!!)
                        if(response.body()?.postStatus=="채팅중")
                            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_chatting_chatroom)
                        if(response.body()?.postStatus=="진행중")
                            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_delivery_chatroom)
                        if(response.body()?.postStatus=="완료")
                            findViewById<ImageView>(R.id.status_chatting_chatroom).setImageResource(R.drawable.ic_status_finish_chatroom)

                        val postStatus = response.body()?.postStatus
                        savePostStatus(this@ChatActivity, response.body()!!.id, postStatus!!)


                    } else {
                        Log.d("error", "Response not successful: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<DataRoomStatus>, t: Throwable) {
                    t.message?.let { Log.d("fail3", t.message.toString()) }
                }

            })
        }

    }
    //poststatus저장
    fun savePostStatus(context: Context, chatRoomId: String, postStatus: String) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("postStatus_$chatRoomId", postStatus)
        editor.apply()
    }

    // chatRoomId에 해당하는 postStatus를 반환
    fun getPostStatus(context: Context, chatRoomId: String): String? {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("postStatus_$chatRoomId", null)
    }

}