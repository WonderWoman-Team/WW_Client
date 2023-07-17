package com.umc.wonder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.umc.wonder.databinding.Login21MainBinding

class SchoolActivity : AppCompatActivity() {
    private var mBinding: Login21MainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login21MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailBtn.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.message, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Login Form")

            val  mAlertDialog = mBuilder.show()


        }
        val intent = Intent(this, EmailActivity::class.java)
        binding.emailBtn.setOnClickListener{startActivity(intent) }
    }
}



