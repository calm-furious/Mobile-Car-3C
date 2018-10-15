package com.example.zhang.a3c_car

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_speech.*


class SpeechActivity : Activity(), SpeechRecognizerTool.ResultsCallback {

    private val mSpeechRecognizerTool = SpeechRecognizerTool(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speech)

        initPermission()

        startSpeechButton.setOnTouchListener {
            view, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    mSpeechRecognizerTool.startASR(this@SpeechActivity)
                }
                MotionEvent.ACTION_UP -> {
                    mSpeechRecognizerTool.stopASR()
                }
                else -> return@setOnTouchListener false
            }

            return@setOnTouchListener true
        }
    }

    override fun onStart() {
        super.onStart()
        mSpeechRecognizerTool.createTool()
    }

    override fun onStop() {
        super.onStop()
        mSpeechRecognizerTool.destroyTool()
    }

    override fun onResults(result: String) {
        this@SpeechActivity.runOnUiThread { speechTextView!!.text = result }
        var flag=0;

        if(result.contains("前进") || result.contains("田径") || result.contains("前景"))
        {
            //Toast.makeText(this@SpeechActivity, "かしこまりました。「前進」しておきます。", Toast.LENGTH_SHORT).show();
            Toast.makeText(this@SpeechActivity, "前进", Toast.LENGTH_SHORT).show();
        }
        else if(result.contains("后退") || result.contains("后腿"))
        {
            //Toast.makeText(this@SpeechActivity, "注意。「後退」。", Toast.LENGTH_SHORT).show();
            Toast.makeText(this@SpeechActivity, "后退", Toast.LENGTH_SHORT).show();
        }
        else if(result.contains("左转"))
        {
            //Toast.makeText(this@SpeechActivity, "「左側」に面白いことがあるんですか。", Toast.LENGTH_SHORT).show();
            Toast.makeText(this@SpeechActivity, "左转", Toast.LENGTH_SHORT).show();
        }
        else if(result.contains("右转"))
        {
            //Toast.makeText(this@SpeechActivity, "「右へ」曲がっています。お待ちください。", Toast.LENGTH_SHORT).show();
            Toast.makeText(this@SpeechActivity, "右转", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Toast.makeText(this@SpeechActivity, "すみません。風の声が大きいですから、聞こえないです。もう一度言ってください。", Toast.LENGTH_SHORT).show();
            Toast.makeText(this@SpeechActivity, "无法识别", Toast.LENGTH_SHORT).show();
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

