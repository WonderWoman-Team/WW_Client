package com.example.wonderwoman

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wonderwoman.databinding.ActivityUserlistGuiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserList :Fragment() {

    lateinit var binding: ActivityUserlistGuiBinding
    lateinit var adapter: UserAdapter

    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef: DatabaseReference

    lateinit var userList: ArrayList<User>


    var userListApi = listOf<list>()
    lateinit var userList1:ArrayList<DataRooms>


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

        api()
        return binding.root


    }


    //retrofit

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
    //인증초기화
//        mAuth= Firebase.auth
//
//        //db초기화
//        mDbRef=Firebase.database.reference
//
//        //리스트초기화
//        userList=ArrayList()

    //
//        adapter=UserAdapter(context = requireContext(), userList = userList)
//
//        binding.userRecyclerview.layoutManager=LinearLayoutManager(requireContext())
//        binding.userRecyclerview.adapter=adapter

    //사용자정보가져오기
//        mDbRef.child("user").addValueEventListener(object:ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for(postSnapShot in snapshot.children){
//                    //유저 정보
//                    val currentUser = postSnapShot.getValue((User::class.java))
//
//                    if(mAuth.currentUser?.uid != currentUser?.uId){
//                        userList.add(currentUser!!)
//                    }
//                }
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                //실패시 실행
//            }

//        })


//    }

    private fun api(){
        Log.d("success","성공")
        RetrofitClassChat.apiRooms.getUser("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJlbWFpbCI6InN5ZW9uMjVAbWp1LmFjLmtyIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTY5MTA5MjY5NywiZXhwIjoxNjkxMTM1ODk3fQ.zYUjSYFp1ekwaziP0P5GoL8MlDUykMCGPO69-VtgT3-2cj_q-KQoStN0jRl2qu5ReOmu13_CpVOlnkHaxq7kyw"
        ).enqueue(object: Callback<DataRooms>{

            override fun onResponse(call: Call<DataRooms>, response: Response<DataRooms>) {

                if(response.isSuccessful){
                    val responseData=response.body()
                    Log.d("success", "total + ${response.code()} + ${response.body()} + ${response.raw()}")


                    userListApi=responseData?.list ?: mutableListOf()

                    adapter=UserAdapter(context = requireContext(), userList = userListApi)

                    binding.userRecyclerview.layoutManager=LinearLayoutManager(requireContext())
                    binding.userRecyclerview.adapter=adapter


                }else{
                    Log.d("error", "total + ${response.code()} + ${response.body()} + ${response.raw()}+ ${response.errorBody()?.string()}")

                }
            }

            override fun onFailure(call: Call<DataRooms>, t: Throwable) {
                t.message?.let { Log.d("fail", t.message.toString()) }
            }

        })
    }


}
