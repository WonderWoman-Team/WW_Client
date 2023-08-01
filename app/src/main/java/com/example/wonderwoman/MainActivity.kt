package com.example.wonderwoman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.FragmentTransaction
import com.example.wonderwoman.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var deliveryFragment: DeliveryFragment
    private lateinit var chatFragment: UserList
    private lateinit var mypageFragment: MypageFragment
    private lateinit var writeBtn: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavBar.setOnItemSelectedListener(onBottomNavItemSelected)
        binding.bottomNavBar.itemIconTintList = null
        deliveryFragment = DeliveryFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragment,deliveryFragment).commit()

        writeBtn = binding.writePost
        writeBtn.visibility = View.VISIBLE
        writeBtn.setOnClickListener {
            var intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private val onBottomNavItemSelected =  NavigationBarView.OnItemSelectedListener {item ->
        when(item.itemId) {
            R.id.delivery_menu -> {
                deliveryFragment = DeliveryFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment,deliveryFragment).commit()
                writeBtn.visibility = View.VISIBLE
            }
            R.id.chat_menu -> {
                val intent = Intent(this, UserList.newInstance()::class.java)
//                startActivity(intent)
//                writeBtn.visibility = View.INVISIBLE
                chatFragment = UserList.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment,chatFragment).commit()
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

}

