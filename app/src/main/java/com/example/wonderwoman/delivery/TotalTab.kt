package com.example.wonderwoman.delivery

import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wonderwoman.R
import com.example.wonderwoman.databinding.TotalTabBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TotalTab : Fragment(){
    private lateinit var totalTabBinding: TotalTabBinding
    private lateinit var liner_btn: CheckBox
    private lateinit var small_btn: CheckBox
    private lateinit var middle_btn: CheckBox
    private lateinit var large_btn: CheckBox
    private lateinit var overnight_btn: CheckBox
    private lateinit var newPostList: ArrayList<Post>

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: PostRecyclerAdapter
    private lateinit var postList: ArrayList<Post>
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    companion object{
        fun newInstance() : TotalTab {
            return TotalTab()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        totalTabBinding = TotalTabBinding.inflate(inflater,container,false)

        liner_btn = totalTabBinding.linerBtn
        small_btn = totalTabBinding.smallBtn
        middle_btn = totalTabBinding.middleBtn
        large_btn = totalTabBinding.largeBtn
        overnight_btn = totalTabBinding.overnightBtn

        recyclerView = totalTabBinding.postRecyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        postList = ArrayList()

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Post")
        databaseReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                for (data in snapshot.children){
                    var listItem = data.getValue(Post::class.java)
                    if (listItem != null) {
                        postList.add(listItem)
                    }
                }
                recyclerAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("POST","${error.toException()}") //에러문 출력
            }
        })
        recyclerAdapter = PostRecyclerAdapter(postList)
        recyclerView.adapter = recyclerAdapter

        //버튼 이벤트
        newPostList = ArrayList()
        var listener = CompoundButton.OnCheckedChangeListener{
                buttonView, isChecked ->
            if(newPostList == postList){newPostList = ArrayList()}
            if(isChecked){
                for (post in postList){
                    when(buttonView.id){
                        R.id.liner_btn -> {if (post.size == "라이너") {newPostList.add(post) }}
                        R.id.small_btn -> {if(post.size=="소형") { newPostList.add(post)}}
                        R.id.middle_btn -> {if(post.size=="중형") { newPostList.add(post)}}
                        R.id.liner_btn -> {if(post.size=="대형") { newPostList.add(post)}}
                        R.id.overnight_btn -> {if(post.size=="오버나이트") { newPostList.add(post)}}
                    }
                }
            }else {
                for (post in postList){
                    when(buttonView.id){
                        R.id.liner_btn -> {if (post.size == "라이너") {newPostList.remove(post)}}
                        R.id.small_btn -> {if(post.size=="소형") { newPostList.remove(post)}}
                        R.id.middle_btn -> {if(post.size=="중형") { newPostList.remove(post)}}
                        R.id.liner_btn -> {if(post.size=="대형") { newPostList.remove(post)}}
                        R.id.overnight_btn -> {if(post.size=="오버나이트") { newPostList.remove(post)}}
                    }
                }
                if(!liner_btn.isChecked && !small_btn.isChecked && !middle_btn.isChecked && !large_btn.isChecked && !overnight_btn.isChecked){
                    if(newPostList.size==0){newPostList=postList}
                }
            }
            recyclerAdapter = PostRecyclerAdapter(newPostList)
            recyclerView.adapter = recyclerAdapter
        }

        liner_btn.setOnCheckedChangeListener(listener)
        small_btn.setOnCheckedChangeListener(listener)
        middle_btn.setOnCheckedChangeListener(listener)
        large_btn.setOnCheckedChangeListener(listener)
        overnight_btn.setOnCheckedChangeListener(listener)

        return totalTabBinding.root
    }
}