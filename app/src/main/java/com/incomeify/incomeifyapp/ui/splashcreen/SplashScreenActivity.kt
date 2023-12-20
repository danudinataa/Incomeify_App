package com.incomeify.incomeifyapp.ui.splashcreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.incomeify.incomeifyapp.R
import com.incomeify.incomeifyapp.ui.auth.AuthActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }, 2300)
    }
}
