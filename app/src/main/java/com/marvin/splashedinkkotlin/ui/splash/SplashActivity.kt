package com.marvin.splashedinkkotlin.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.marvin.splashedinkkotlin.R
import com.marvin.splashedinkkotlin.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.backgroundResource

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        img_splash.backgroundResource = R.drawable.splash

        Handler().postDelayed({
            kotlin.run {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 2000)
    }
}
