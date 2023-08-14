package com.example.wonderwoman.delivery

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.MainActivity
import com.example.wonderwoman.R
import com.example.wonderwoman.databinding.ActivityPostBinding
import com.example.wonderwoman.databinding.ToastBinding
import com.example.wonderwoman.model.RetrofitClass
import com.example.wonderwoman.model.delivery.RequestAddPost
import com.example.wonderwoman.model.delivery.ResponseAddPost
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PostActivity : AppCompatActivity() {
    private lateinit var toastBinding: ToastBinding
    private lateinit var toastView: TextView
    private lateinit var binding: ActivityPostBinding
    private lateinit var completeBtn: Button
    private lateinit var quitBtn: Button
    private lateinit var postTitle: EditText
    private lateinit var postCount: EditText
    private lateinit var postSignificant: EditText
    private lateinit var categoryGroup: RadioGroup
    private lateinit var requestBtn: RadioButton
    private lateinit var dispatchBtn: RadioButton
    private lateinit var sizeGroup: RadioGroup
    private lateinit var linerBtn: Button
    private lateinit var smallBtn: Button
    private lateinit var regularBtn: Button
    private lateinit var largeBtn: Button
    private lateinit var overnightBtn: Button
    private lateinit var typeGroup: RadioGroup
    private lateinit var wingBtn: Button
    private lateinit var absorptionBtn: Button
    private lateinit var cottonBtn: Button
    private lateinit var organicBtn: Button

//    var newPost = Post("", "", "", "", "", "", "", "")
//
//    private lateinit var database: FirebaseDatabase
//    private lateinit var databaseReference: DatabaseReference
//
//    var index = 11

    var data: ResponseAddPost? = null
    var status: String? = ""
    var result: String = ""
    var errMsg: String? = ""

    var requestAddPost: RequestAddPost = RequestAddPost(mutableListOf(),"","",0,"","","")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        toastBinding = ToastBinding.inflate(layoutInflater)
        toastView = toastBinding.toast
        setContentView(binding.root)

        completeBtn = binding.completeBtn
        quitBtn = binding.quitBtn
        postTitle = binding.posttitle
        categoryGroup = binding.categoryGroup
        requestBtn = binding.requestBtn
        dispatchBtn = binding.dispatchBtn
        postCount = binding.postcount
        sizeGroup = binding.sizeGroup
        linerBtn = binding.linerBtn
        smallBtn = binding.smallBtn
        regularBtn = binding.regularBtn
        largeBtn = binding.largeBtn
        overnightBtn = binding.overnightBtn
        typeGroup = binding.typeGroup
        wingBtn = binding.wingBtn
        absorptionBtn = binding.absorptionBtn
        cottonBtn = binding.cottonBtn
        organicBtn = binding.organicBtn
        postSignificant = binding.postsignificant

//        supportFragmentManager.beginTransaction().add(com.example.wonderwoman.R.id.fragment,UserList.newInstance()).commit()

        //제목 입력 감지
        postTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                requestAddPost.postTitle = postTitle.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //유형 선택 감지
        categoryGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == requestBtn.id) requestAddPost.postReqType = "${requestBtn.text}글"
            else if (checkedId == dispatchBtn.id) requestAddPost.postReqType = "${dispatchBtn.text}글"
        }

        //개수 입력 감지
        postCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                requestAddPost.sanitaryNum = postCount.text.toString().toInt();
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //크기 선택 감지
        sizeGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                linerBtn.id -> requestAddPost.sanitarySize = linerBtn.text.toString()
                smallBtn.id -> requestAddPost.sanitarySize = smallBtn.text.toString()
                regularBtn.id -> requestAddPost.sanitarySize = regularBtn.text.toString()
                largeBtn.id -> requestAddPost.sanitarySize = largeBtn.text.toString()
                overnightBtn.id -> requestAddPost.sanitarySize = overnightBtn.text.toString()
            }
        }

        //종류 선택 감지
        typeGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                wingBtn.id -> requestAddPost.sanitaryType = wingBtn.text.toString()
                absorptionBtn.id -> requestAddPost.sanitaryType = absorptionBtn.text.toString()
                cottonBtn.id -> requestAddPost.sanitaryType = cottonBtn.text.toString()
                organicBtn.id -> requestAddPost.sanitaryType = organicBtn.text.toString()
            }
        }

        //완료 버튼 감지
        completeBtn.setOnClickListener {
            status = addPost(requestAddPost)
            if(status == "SUCCESS") {
                toastView.text = "게시글이 성공적으로 등록되었습니다!"
            }else if(status == "400"){
                toastView.text = "건물이 학교와 매칭되지 않습니다"
            }
            else{
                toastView.text = "error"
            }
            val view1 = layoutInflater.inflate(R.layout.toast, null)
            var toast = Toast(this)
            toast.view = view1
            toast.setGravity(Gravity.TOP,0,0)
            toast.duration = Toast.LENGTH_LONG
            toast.show()
            Log.d("toast", toastView.text as String)
            //mainactivity로 전환
            var intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        //X 버튼 감지
        quitBtn.setOnClickListener {
            var intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }


        //edittext 스크롤 기능
        postCount.setOnTouchListener { v, event ->
            if (v.id === postCount.id) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> v.parent
                        .requestDisallowInterceptTouchEvent(false)
                }
            }
            false
        }
        postSignificant.setOnTouchListener { v, event ->
            if (v.id === postSignificant.id) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> v.parent
                        .requestDisallowInterceptTouchEvent(false)
                }
            }
            false
        }
    }

    //외부 클릭 시 키보드 내리게
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    private fun addPost(requestAddPost: RequestAddPost): String? {
        val callAddPost: Call<ResponseAddPost> =
            RetrofitClass.deliveryAPI.addDeliveryPost(requestAddPost)
        Log.d("fetchAdd","${callAddPost==null}")
        callAddPost.enqueue(object : retrofit2.Callback<ResponseAddPost>{
            override fun onResponse(
                call: Call<ResponseAddPost>,
                response: Response<ResponseAddPost>
            ) {
                response.takeIf { it.isSuccessful }?.body()?.let { it ->
                    data = response.body()
                    Log.d("success", data.toString())
                    status = data!!.status
                } ?: showError(response.body())
//                } ?: showError(response.errorBody())
            }

            override fun onFailure(call: Call<ResponseAddPost>, t: Throwable) {
                t.message?.let { Log.d("fail", it) }
            }
        })
        return status
    }
    fun showError(data: ResponseAddPost?) {
        if (data != null) {
            errMsg = data.solution
        }
    }
//    fun showError(error: ResponseBody?) {
//        val e = error ?: return
//        val ob = JSONObject(e.string())
//        Log.d("error", ob.getString("message"))
//    }
}