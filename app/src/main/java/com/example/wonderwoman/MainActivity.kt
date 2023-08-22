package com.example.wonderwoman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.example.wonderwoman.databinding.ActivityMainBinding
import com.example.wonderwoman.delivery.DeliveryFragment
import com.example.wonderwoman.delivery.PostActivity
import com.example.wonderwoman.mypage.EditInfoFragment
import com.example.wonderwoman.mypage.MypageFragment
import com.example.wonderwoman.util.Constants.EWHA
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var deliveryFragment: DeliveryFragment
    private lateinit var userList: UserList
    private lateinit var mypageFragment: MypageFragment
    private lateinit var editInfoFragment: EditInfoFragment
    private lateinit var writeBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //학교 값 넘겨받기
        val input = intent.getStringExtra("School")
        binding.schooldata.setText(input)
        binding.schooldata.text= Editable.Factory.getInstance().newEditable(input)

        binding.bottomNavBar.setOnItemSelectedListener(onBottomNavItemSelected)
        binding.bottomNavBar.itemIconTintList = null
        deliveryFragment = DeliveryFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragment,deliveryFragment).commit()

        writeBtn = binding.writePost
        writeBtn.visibility = View.VISIBLE
        writeBtn.setOnClickListener {
            var intent = Intent(this, PostActivity::class.java)
            intent.putExtra("school",EWHA)
            startActivity(intent)
            finish()
        }
    }

    //외부 클릭 시 키보드 내리게
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        if(currentFocus is EditText) {
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    private val onBottomNavItemSelected =  NavigationBarView.OnItemSelectedListener {item ->
        when(item.itemId) {
            R.id.delivery_menu -> {
                deliveryFragment = DeliveryFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment,deliveryFragment).commit()
                writeBtn.visibility = View.VISIBLE
            }
            R.id.chat_menu -> {
//                val intent = Intent(this, UserList.newInstance()::class.java)
//                startActivity(intent)
//                writeBtn.visibility = View.INVISIBLE
//                chatFragment = UserList.newInstance()
                userList = UserList.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment,userList).commit()
                writeBtn.visibility = View.INVISIBLE
            }
            R.id.mypage_menu -> {
                mypageFragment = MypageFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment, mypageFragment).commit()
                writeBtn.visibility = View.INVISIBLE
            }
        }
        true
    }

    fun changeFragment(index: Int){
        when(index){
            1 -> {
                editInfoFragment = EditInfoFragment.newInstance()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, editInfoFragment).addToBackStack(null)
                    .commit()
            }

            2 -> {
                mypageFragment = MypageFragment.newInstance()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, mypageFragment)
                    .commit()
            }
            3-> {
                userList = UserList.newInstance()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment, userList)
                    .commit()
                writeBtn.visibility = View.INVISIBLE
                binding.bottomNavBar.selectedItemId = R.id.chat_menu
            }
        }
    }
}