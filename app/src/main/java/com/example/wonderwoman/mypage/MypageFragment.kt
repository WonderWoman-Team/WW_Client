package com.example.wonderwoman.mypage

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.wonderwoman.main.MainActivity
import com.example.wonderwoman.R
import com.example.wonderwoman.databinding.FragmentMypageBinding
import com.example.wonderwoman.model.RetrofitClass
import com.example.wonderwoman.model.mypage.ResponseMyInfo
import retrofit2.Call
import retrofit2.Response

class MypageFragment : Fragment() {
    var data: ResponseMyInfo? = null
    var myInfo: ResponseMyInfo? = null
    private lateinit var mypageBinding: FragmentMypageBinding
    private lateinit var profileImg: ImageView
    private lateinit var editInfoBtn: ImageButton
    private lateinit var nickname: TextView
    private lateinit var email: TextView

    var mainActivity: MainActivity? = null
    var bitmap: Bitmap? = null
    private lateinit var accessToken: String
    companion object {
        fun newInstance(): MypageFragment {
            return MypageFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
//        accessToken = (activity as MainActivity).getToken().toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mypageBinding = FragmentMypageBinding.inflate(inflater, container, false)
        profileImg = mypageBinding.profileImg
        nickname = mypageBinding.nickname
        email = mypageBinding.email
        editInfoBtn = mypageBinding.editInfo
        accessToken = mainActivity?.getToken().toString()

        fetchMyInfo()

        editInfoBtn.setOnClickListener {
            Log.d("mypage", "clicked")
            mainActivity!!.changeFragment(1)
//            val fragment = EditInfoFragment.newInstance()
//            fragment.getAccessToken(accessToken)
        }

        return mypageBinding.root
    }

    private fun fetchMyInfo(): ResponseMyInfo? {
        val callGetMyInfo: Call<ResponseMyInfo> =
            RetrofitClass.mypageAPI.getMyInfo(accessToken)
        Log.d("mypage",accessToken)
        callGetMyInfo.enqueue(object : retrofit2.Callback<ResponseMyInfo> {
            override fun onResponse(
                call: Call<ResponseMyInfo>,
                response: Response<ResponseMyInfo>
            ) {
                if (response.isSuccessful) {

                    val result: Response<ResponseMyInfo> = response
                    Log.d(
                        "success",
                        "myinfo + ${result.code()} + ${result.body()} + ${result.raw()}"
                    )
                    data = result.body()
                    nickname.text = data?.nickname ?: ""
                    email.text = data?.email ?: ""
                    if (data?.imgUrl == null) profileImg.setImageResource(R.drawable.profile)
                    else {
                        profileImg.setImageResource(R.drawable.profile)
                    }
                } else {
                    val result: Response<ResponseMyInfo> = response
                    Log.d("fail", "total + ${result.code()} + ${result.body()} + ${result.raw()} + ${result.errorBody()?.string()}")

//                    showError(response.errorBody())
                }
            }

            override fun onFailure(call: Call<ResponseMyInfo>, t: Throwable) {
                //통신 실패 로직
                t.message?.let { Log.d("fail post", t.message.toString()) }
            }
        })
        return data
    }

//    fun showError(error: ResponseBody?) {
//        val e = error ?: return
//        val ob = JSONObject(e.string())
//        Log.d("error myinfo", ob.getString("solution"))
//    }

}