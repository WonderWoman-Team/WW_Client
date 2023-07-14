package com.example.wonderwoman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.example.wonderwoman.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding
    private lateinit var completeBtn: Button
    private lateinit var quitBtn: Button
    private lateinit var postTitle: EditText
    private lateinit var postCount: EditText
    private lateinit var postSignificant: EditText
    private lateinit var categotyGroup: RadioGroup
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

    }

    //외부 클릭 시 키보드 내리게
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

}