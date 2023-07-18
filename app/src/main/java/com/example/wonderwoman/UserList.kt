package com.example.wonderwoman

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wonderwoman.databinding.ActivityUserlistBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserList :AppCompatActivity() {

    lateinit var binding: ActivityUserlistBinding
    lateinit var adapter: UserAdapter

    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef: DatabaseReference

    lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인증초기화
        mAuth= Firebase.auth

        //db초기화
        mDbRef=Firebase.database.reference

        //리스트초기화
        userList=ArrayList()

        //
        adapter=UserAdapter(this,userList)

        binding.userRecyclerview.layoutManager=LinearLayoutManager(this)
        binding.userRecyclerview.adapter=adapter

        //사용자정보가져오기
        mDbRef.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(postSnapShot in snapshot.children){
                    //유저 정보
                    val currentUser = postSnapShot.getValue((User::class.java))

                    if(mAuth.currentUser?.uid != currentUser?.uId){
                        userList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                //실패시 실행
            }

        })


    }
}