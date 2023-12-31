package com.example.wonderwoman

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.wonderwoman.databinding.Login2MainBinding


class SchoolActivity : AppCompatActivity() {
    private var mBinding: Login2MainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = Login2MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build()
        )
        val items = resources.getStringArray(R.array.university)
        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = myAdapter
        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position){
                    1, 2 -> {
                        binding.schoolText.text = items[position]
                    }
                }
                0           }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        binding.btnSend!!.setOnClickListener {
            val mailServer = SendMail()
            mailServer.sendSecurityCode(applicationContext, binding.editEmail!!.text.toString())
            binding.seeText.visibility= View.GONE
            binding.seeEdit.visibility= View.VISIBLE
            val view1 = layoutInflater.inflate(R.layout.message, null)
            var toast = Toast(this)
            toast.view = view1
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        }
        //다음페이지
        binding.nextBtn1.setOnClickListener{
            val email=binding.editEmail.getText().toString()
            val school=binding.schoolText.getText().toString()
            val intent = Intent(this, PasswordActivity::class.java)
            intent.putExtra("Email", email)
            intent.putExtra("School", school)
            startActivity(intent) }

        //이전페이지
        val intent2 = Intent(this, Main2Activity::class.java)
        binding.back1.setOnClickListener{startActivity(intent2) }
    }
}






