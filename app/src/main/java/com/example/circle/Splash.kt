package com.example.circle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class Splash : AppCompatActivity() {
    val SPLASH_TIME_OUT:Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val myanim=AnimationUtils.loadAnimation(this@Splash, R.anim.splashanimation)
        Handler().postDelayed(Runnable{
            run {
                val intent = Intent(this@Splash,SignIn::class.java)
                startActivity(intent)
                finish()
            }
        },SPLASH_TIME_OUT)

        logoSplash.startAnimation(myanim)

    }
}
