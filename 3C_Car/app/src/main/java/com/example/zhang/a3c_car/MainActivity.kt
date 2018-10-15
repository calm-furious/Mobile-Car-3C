package com.example.zhang.a3c_car

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import com.example.zhang.a3c_car.R.layout.activity_main
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity(){

    //private val mSpeechRecognizerTool = SpeechRecognizerTool(this)

    private var b1: Button? = null  //先声明
    private var b2: Button? = null
    private var b3: Button? = null
    private var isOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        b1 = findViewById(R.id.SpeechButton) as Button

        initPermission()


/*
        SpeechButton.setOnClickListener() {
            view ->
            var intent = Intent(this,SpeechActivity::class.java)
            startActivity(intent)

            //return@setOnClickListener
        }
        */
        SpeechButton.setOnTouchListener {
            view, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {

                }
                MotionEvent.ACTION_UP -> {
                    var intent = Intent(this,SpeechActivity::class.java)
                    startActivity(intent)
                }
                else -> return@setOnTouchListener false
            }

            return@setOnTouchListener true
        }
        FlipButton.setOnTouchListener {
            view, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {

                }
                MotionEvent.ACTION_UP -> {
                    var intent = Intent(this,FlipActivity::class.java)
                    startActivity(intent)
                }
                else -> return@setOnTouchListener false
            }

            return@setOnTouchListener true
        }
    }




    private fun initPermission() {
        val permissions = arrayOf<String>(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val toApplyList = ArrayList<String>()

        for (perm in permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm)
                //进入到这里代表没有权限.

            }
        }
        val tmpList = arrayOfNulls<String>(toApplyList.size)
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123)
        }
    }
}

