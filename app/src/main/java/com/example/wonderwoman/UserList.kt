package com.example.wonderwoman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wonderwoman.databinding.ActivityUserlistBinding
import com.example.wonderwoman.databinding.ActivityUserlistGuiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserList :Fragment() {

    lateinit var binding: ActivityUserlistGuiBinding
    lateinit var adapter: UserAdapter

    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef: DatabaseReference

    lateinit var userList: ArrayList<User>

    companion object {
        fun newInstance() : UserList {
            return UserList()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityUserlistGuiBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //인증초기화
        mAuth= Firebase.auth

        //db초기화
        mDbRef=Firebase.database.reference

        //리스트초기화
        userList=ArrayList()

        //
        adapter=UserAdapter(context = requireContext(), userList = userList)

        binding.userRecyclerview.layoutManager=LinearLayoutManager(requireContext())
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