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
import com.example.wonderwoman.databinding.DispatchTabBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DispatchTab : Fragment(){
    private lateinit var dispatchTabBinding: DispatchTabBinding
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
        fun newInstance() : DispatchTab {
            return DispatchTab()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dispatchTabBinding = DispatchTabBinding.inflate(inflater,container,false)

      liner_btn = dispatchTabBinding.linerBtn
        small_btn = dispatchTabBinding.smallBtn
        middle_btn = dispatchTabBinding.middleBtn
        large_btn = dispatchTabBinding.largeBtn
        overnight_btn = dispatchTabBinding.overnightBtn

        recyclerView = dispatchTabBinding.postRecyclerview //리사이클러뷰 연결
        recyclerView.setHasFixedSize(true) //recyclerview 성능 강화
        recyclerView.layoutManager = LinearLayoutManager(activity)
        postList = ArrayList() //Post 객체를 담을 어레이리스트 (어댑터쪽으로)

        //firebase
        database = FirebaseDatabase.getInstance() //firebase의 기능을 database에 연동
        databaseReference = database.getReference("Post") //DB테이블 연결
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //firebase의 database 받아오는 곳
                postList.clear() //초기화
                for (data in snapshot.children){ //데이터리스트 추출
                    var listItem = data.getValue(Post::class.java)

                    if (listItem!!.post_state == "출동글") {
                        postList.add(listItem)
                    }
                }
                recyclerAdapter.notifyDataSetChanged() //리스트 저장 및 새로고침
            }

            override fun onCancelled(error: DatabaseError) {
                //db를 가져오던 중 에러가 발생 시
                Log.d("POST","${error.toException()}") //에러문 출력
            }
        })
        recyclerAdapter = PostRecyclerAdapter(postList)
        recyclerView.adapter = recyclerAdapter //recyclerview에 어댑터 연결

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
        return dispatchTabBinding.root
    }
}