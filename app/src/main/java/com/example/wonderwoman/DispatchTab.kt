package com.example.wonderwoman

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.wonderwoman.databinding.DispatchTabBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DispatchTab : Fragment(){
    private lateinit var dispatchTabBinding: DispatchTabBinding

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
                    if (listItem != null) {
                        postList.add(listItem) //담은 데이터를 리사이클러뷰로 보낼 준비
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
        recyclerView.adapter = recyclerAdapter //recyclervie에 어댑터 연결

        return dispatchTabBinding.root
    }
}