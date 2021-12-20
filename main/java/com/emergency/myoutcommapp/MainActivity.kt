package com.emergency.myoutcommapp

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.widget.EditText


class MainActivity : AppCompatActivity() {
    var picha:Button? = null
    var stk:Button? = null
    var sms:Button? = null
    var emai:Button? = null
    var shar:Button? = null
    var pigasimu:Button? = null
    var phonenumber:EditText? = null
    var message:EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        picha = findViewById(R.id.camerabtn)
        stk = findViewById(R.id.stkbtn)
        sms = findViewById(R.id.smsbtn)
        emai = findViewById(R.id.emailbtn)
        shar = findViewById(R.id.sharebtn)
        pigasimu = findViewById(R.id.callbtn)
        phonenumber = findViewById(R.id.editTextPhone)
        message = findViewById(R.id.edtMessage)

        picha!!.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, 1)
        }
        stk!!.setOnClickListener {
            val simToolKitLaunchIntent =
                applicationContext.packageManager.getLaunchIntentForPackage("com.android.stk")
            simToolKitLaunchIntent?.let { startActivity(it) }
        }
        sms!!.setOnClickListener {
           var phoneNumber = phonenumber!!.text.toString().trim()
            var message = message!!.text.toString()
            if (phoneNumber.isEmpty()){
                phonenumber!!.setError("enter your phone number kindly")
                phonenumber!!.requestFocus()
            }else{

            val uri: Uri = Uri.parse("smsto:$phoneNumber")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", "$message")
            startActivity(intent)
            }
        }

        emai!!.setOnClickListener {
            val emailIntent =
                Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "abc@gmail.com", null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }
        shar!!.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, download this app!")
            startActivity(shareIntent)
        }
        pigasimu!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+254716781385"))
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    1
                )
            } else {
                startActivity(intent)
            }
        }

    }
}