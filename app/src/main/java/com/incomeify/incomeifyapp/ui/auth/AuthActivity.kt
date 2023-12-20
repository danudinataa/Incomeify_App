package com.incomeify.incomeifyapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.incomeify.incomeifyapp.R
import com.incomeify.incomeifyapp.ui.auth.onboarding.OnboardingFragment
import com.incomeify.incomeifyapp.ui.auth.onboarding.OnboardingFragment2
import com.incomeify.incomeifyapp.ui.auth.onboarding.OnboardingFragment3
import com.incomeify.incomeifyapp.ui.auth.login.LoginFragment
import com.incomeify.incomeifyapp.ui.auth.register.RegisterFragment
import com.incomeify.incomeifyapp.data.session.SharedPreferencesManager
import com.incomeify.incomeifyapp.ui.dashboard.DashboardActivity


class AuthActivity : AppCompatActivity(),
    OnboardingFragment.OnboardingNavigationListener,
    OnboardingFragment2.OnboardingNavigationListener,
    OnboardingFragment3.OnboardingNavigationListener,
    LoginFragment.LoginNavigationListener,
    RegisterFragment.RegisterNavigationListener {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        sharedPreferencesManager = SharedPreferencesManager(this)

        if (!sharedPreferencesManager.getAuthToken().isNullOrEmpty()) {
            navigateToDashboard()
        } else {
            val directLogin = intent.getBooleanExtra("directLogin", false)
            if (directLogin) {
                // Langsung tampilkan LoginFragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment().apply {
                        setLoginNavigationListener(this@AuthActivity)
                    })
                    .commit()
            } else if (savedInstanceState == null) {
                // Tampilkan OnboardingFragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, OnboardingFragment().apply {
                        setOnboardingNavigationListener(this@AuthActivity)
                    })
                    .commit()
            }
        }
    }


    private fun navigateToDashboard() {
        // Implementasikan navigasi ke DashboardActivity
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onNextClicked() {
        val nextFragment = when (supportFragmentManager.findFragmentById(R.id.fragment_container)) {
            is OnboardingFragment -> OnboardingFragment2().apply {
                setOnboardingNavigationListener(this@AuthActivity)
            }
            is OnboardingFragment2 -> OnboardingFragment3().apply {
                setOnboardingNavigationListener(this@AuthActivity)
            }
            else -> return
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out)
            .replace(R.id.fragment_container, nextFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackClicked() {
        val backFragment = when (supportFragmentManager.findFragmentById(R.id.fragment_container)) {
            is OnboardingFragment3 -> OnboardingFragment2().apply {
                setOnboardingNavigationListener(this@AuthActivity)
            }
            is OnboardingFragment2 -> OnboardingFragment().apply {
                setOnboardingNavigationListener(this@AuthActivity)
            }
            else -> return
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out)
            .replace(R.id.fragment_container, backFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onStartClicked() {
        val loginFragment = LoginFragment().apply {
            setLoginNavigationListener(this@AuthActivity)
        }
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out)
            .replace(R.id.fragment_container, loginFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToRegister() {
        val registerFragment = RegisterFragment().apply {
            setRegisterNavigationListener(this@AuthActivity)
        }
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out)
            .replace(R.id.fragment_container, registerFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToLogin() {
        val loginFragment = LoginFragment().apply {
            setLoginNavigationListener(this@AuthActivity)
        }
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out)
            .replace(R.id.fragment_container, loginFragment)
            .addToBackStack(null)
            .commit()
    }

}
