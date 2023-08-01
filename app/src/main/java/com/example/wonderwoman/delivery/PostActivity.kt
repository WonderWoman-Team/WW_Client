package com.example.wonderwoman.delivery

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.MainActivity
import com.example.wonderwoman.databinding.ActivityPostBinding


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
    private lateinit var typeGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        completeBtn = binding.completeBtn
        quitBtn = binding.quitBtn
        completeBtn.setOnClickListener {
//            var intent = Intent(this, PostActivity::class.java)
//            startActivity(intent)
//            finish()
        }
        quitBtn.setOnClickListener {
            var intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        postTitle = binding.posttitle
        postCount = binding.postcount
        postSignificant = binding.postsignificant
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

        categoryGroup = binding.categoryGroup
        requestBtn = binding.requestBtn
        dispatchBtn = binding.dispatchBtn
        categoryGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                if(checkedId == requestBtn.id){
                    requestBtn.text
                }else if(checkedId == dispatchBtn.id){

                }
            }

        })
    }

    //외부 클릭 시 키보드 내리게
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

}