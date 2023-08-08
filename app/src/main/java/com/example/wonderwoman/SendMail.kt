package com.example.wonderwoman

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import javax.mail.MessagingException
import javax.mail.SendFailedException


class SendMail : AppCompatActivity() {
    var user = "jsm66780495@gmail.com" // 보내는 계정의 id
    var password = "vrnvfrxsviwcklbt" // 보내는 계정의 pw
    var gMailSender = GMailSender(user, password)
    var emailCode = gMailSender.emailCode
    fun sendSecurityCode(context: Context?, sendTo: String?) {
        try {
            gMailSender.sendMail("WonderWoman 인증 메일입니다.", "인증번호는 12345 입니다.", sendTo!!)
        } catch (e: SendFailedException) {
            Toast.makeText(context, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
        } catch (e: MessagingException) {
            Toast.makeText(context, "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}