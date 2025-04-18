package com.example.newsapp.feature.splashscreen.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.newsapp.core.base.BaseActivity
import com.example.newsapp.core.session.PreferenceProvider
import com.example.newsapp.databinding.ActivityMainSplashScreenBinding
import com.example.newsapp.feature.login.view.LoginActivity
import com.example.newsapp.feature.main.view.MainActivity
import org.koin.android.ext.android.inject

class MainActivitySplashScreenActivity : BaseActivity<ActivityMainSplashScreenBinding>() {
    private val mPreferenceProvider: PreferenceProvider by inject()

    override fun getViewBinding(): ActivityMainSplashScreenBinding {
        return ActivityMainSplashScreenBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        Handler().postDelayed({
            val intent =
                Intent(this, if (mPreferenceProvider.isLogin()) MainActivity::class.java else LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}