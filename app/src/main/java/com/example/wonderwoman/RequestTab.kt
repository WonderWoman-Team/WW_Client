package com.example.wonderwoman

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wonderwoman.databinding.RequestTabBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RequestTab : Fragment(){
    private lateinit var requestTabBinding: RequestTabBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: PostRecyclerAdapter
    private lateinit var postList: ArrayList<Post>
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    companion object{
        fun newInstance() : RequestTab {
            return RequestTab()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestTabBinding = RequestTabBinding.inflate(inflater,container,false)

        recyclerView = requestTabBinding.postRecyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        postList = ArrayList()

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Post")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                for (data in snapshot.children){
                    var listItem = data.getValue(Post::class.java)

                    if (listItem!!.post_state == "요청글") {
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

        return requestTabBinding.root
    }
}