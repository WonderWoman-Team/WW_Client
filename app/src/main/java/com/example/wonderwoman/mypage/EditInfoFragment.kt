package com.example.wonderwoman.mypage

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.wonderwoman.MainActivity
import com.example.wonderwoman.R
import com.example.wonderwoman.databinding.FragmentEditInfoBinding
import com.example.wonderwoman.model.RetrofitClass
import com.example.wonderwoman.model.mypage.RequestEditMyInfo
import com.example.wonderwoman.model.mypage.ResponseMyInfo
import com.example.wonderwoman.util.BitmapConverter
import com.example.wonderwoman.util.Constants.ACCESS_TOKEN
import com.example.wonderwoman.util.CustomToast
import com.example.wonderwoman.util.GetAccessToken
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditInfoFragment : Fragment(){
    private lateinit var editInfoBinding: FragmentEditInfoBinding
    private lateinit var backBtn: ImageButton
    private lateinit var profileImg: ImageView
    private lateinit var editImgBtn: ImageButton
    private lateinit var newNickname: EditText
    private lateinit var password: EditText
    private lateinit var newPassword: EditText
    private lateinit var wrongPw: TextView
    private lateinit var saveBtn: ImageButton
    var mainActivity: MainActivity? = null
    var requestEditMyInfo = RequestEditMyInfo(null, null, null)
    var pw: String? = null
    var newPw: String? = null

    val REQUEST_CODE = 200 //request code
    private lateinit var accessToken: String


    companion object {
        fun newInstance(): EditInfoFragment {
            return EditInfoFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        mainActivity = context as MainActivity
        accessToken = (activity as MainActivity).getToken().toString()

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editInfoBinding = FragmentEditInfoBinding.inflate(inflater, container, false)
        backBtn = editInfoBinding.backBtn
        profileImg = editInfoBinding.profileImg
        editImgBtn = editInfoBinding.editImgBtn
        newNickname = editInfoBinding.newNickname
        password = editInfoBinding.editpw
        newPassword = editInfoBinding.editpw2
        wrongPw = editInfoBinding.wrongPw
        saveBtn = editInfoBinding.saveEditinfoBtn
        mainActivity = context as MainActivity

        editImgBtn.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(intent, REQUEST_CODE)
        }

        newNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(newNickname.text == null) requestEditMyInfo.nickname = newNickname.hint.toString()
                else requestEditMyInfo.nickname = newNickname.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                pw = password.text.toString()
                if (pw == newPw || (password.text.toString() == null && newPassword.text.toString() == null) || pw == null) {
                    wrongPw.visibility = View.INVISIBLE
                    newPassword.setBackgroundResource(R.drawable.edittext_blue)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        newPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                newPw = newPassword.text.toString()
                if (pw != newPw) {
                    wrongPw.visibility = View.VISIBLE
                    newPassword.setBackgroundResource(R.drawable.edittext_red)
                } else if (pw == newPw || (password.text.toString() == null && newPassword.text.toString() == null) || pw == null) {
                    wrongPw.visibility = View.INVISIBLE
                    newPassword.setBackgroundResource(R.drawable.edittext_blue)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        backBtn.setOnClickListener {
            mainActivity!!.changeFragment(2)
        }

        saveBtn.setOnClickListener {
            if (wrongPw.visibility == View.INVISIBLE) {
                if (pw == newPw) requestEditMyInfo.password = newPw
                fetchEditInfo(requestEditMyInfo)
            }
        }

        //edittext 스크롤 기능
        newNickname.setOnTouchListener(touchListener)
        password.setOnTouchListener(touchListener)
        newPassword.setOnTouchListener(touchListener)

        return editInfoBinding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK){return}
        when(requestCode){
            REQUEST_CODE -> {
                data?:return
                val uri = data.data as Uri

                Log.d("uri", uri.toString())
                profileImg.setImageURI(uri)
                requestEditMyInfo.imgUrl = uri.toString()
            }
            else -> {
                context?.let { CustomToast.showToast(it, "사진을 가져오지 못했습니다") }
            }
        }
    }
//        override fun getAccessToken(token: String) {
//        //토큰 받아옴
//        accessToken = token
//
//    }
    private val touchListener = View.OnTouchListener { v, event ->
        if (v.id == newNickname.id || v.id == password.id || v.id == newPassword.id)
            v.parent.requestDisallowInterceptTouchEvent(true)
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> v.parent
                .requestDisallowInterceptTouchEvent(false)
        }
        false
    }

    fun fetchEditInfo(requestEditMyInfo: RequestEditMyInfo) {
        Log.d("editinfo", requestEditMyInfo.toString())
        val callEditMyInfo: Call<ResponseMyInfo> =
            RetrofitClass.mypageAPI.editMyInfo(accessToken, requestEditMyInfo)
        callEditMyInfo.enqueue(object : Callback<ResponseMyInfo> {
            override fun onResponse(
                call: Call<ResponseMyInfo>,
                response: Response<ResponseMyInfo>
            ) {
                if (response.isSuccessful) {
                    val result: Response<ResponseMyInfo> = response
                    Log.d(
                        "success",
                        "editinfo + ${result.code()} + ${result.body()} + ${result.raw()}"
                    )
                    mainActivity = context as MainActivity
                    mainActivity!!.changeFragment(2)
                } else {
                    showError(response.errorBody())
                }
            }

            override fun onFailure(call: Call<ResponseMyInfo>, t: Throwable) {
                t.message?.let { Log.d("fail edit", t.message.toString()) }
            }

        })

    }

    fun showError(error: ResponseBody?) {
        val e = error ?: return
        val ob = JSONObject(e.string())
        CustomToast.showToast(requireContext(), ob.getString("solution"))
    }
}