package com.example.wonderwoman.delivery

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.MainActivity
import com.example.wonderwoman.databinding.ActivityPostBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PostActivity : AppCompatActivity() {
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

    var newPost = Post("", "", "", "", "", "", "", "")

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    var index = 11

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
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
                newPost.location = postTitle.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //유형 선택 감지
        categoryGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == requestBtn.id) newPost.post_state = "${requestBtn.text}글"
            else if (checkedId == dispatchBtn.id) newPost.post_state = "${dispatchBtn.text}글"
        }

        //개수 입력 감지
        postCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                newPost.count = postCount.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //크기 선택 감지
        sizeGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                linerBtn.id -> newPost.size = linerBtn.text.toString()
                smallBtn.id -> newPost.size = smallBtn.text.toString()
                regularBtn.id -> newPost.size = regularBtn.text.toString()
                largeBtn.id -> newPost.size = largeBtn.text.toString()
                overnightBtn.id -> newPost.size = overnightBtn.text.toString()
            }
        }

        //종류 선택 감지
        typeGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                wingBtn.id -> newPost.type = wingBtn.text.toString()
                absorptionBtn.id -> newPost.type = absorptionBtn.text.toString()
                cottonBtn.id -> newPost.type = cottonBtn.text.toString()
                organicBtn.id -> newPost.type = organicBtn.text.toString()
            }
        }

        //완료 버튼 감지
        completeBtn.setOnClickListener {
            var dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
            newPost.time = dateTime
            newPost.nickname = "본인"

            database = FirebaseDatabase.getInstance()
            databaseReference = database.getReference("Post")
            databaseReference.child("Post_${index}").setValue(newPost)
            index++
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
}