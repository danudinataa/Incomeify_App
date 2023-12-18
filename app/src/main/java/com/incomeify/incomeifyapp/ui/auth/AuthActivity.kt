package com.incomeify.incomeifyapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.incomeify.incomeifyapp.R
import com.incomeify.incomeifyapp.ui.auth.onboarding.OnboardingFragment
import com.incomeify.incomeifyapp.ui.auth.onboarding.OnboardingFragment2
import com.incomeify.incomeifyapp.ui.auth.onboarding.OnboardingFragment3
import com.incomeify.incomeifyapp.ui.auth.login.LoginFragment
import com.incomeify.incomeifyapp.ui.auth.register.RegisterFragment

class AuthActivity : AppCompatActivity(),
    OnboardingFragment.OnboardingNavigationListener,
    OnboardingFragment2.OnboardingNavigationListener,
    OnboardingFragment3.OnboardingNavigationListener,
    LoginFragment.LoginNavigationListener,
    RegisterFragment.RegisterNavigationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OnboardingFragment().apply {
                    setOnboardingNavigationListener(this@AuthActivity)
                })
                .commit()
        }
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
            .replace(R.id.fragment_container, nextFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackClicked() {
        supportFragmentManager.popBackStack()
    }

    override fun onStartClicked() {
        val loginFragment = LoginFragment().apply {
            setLoginNavigationListener(this@AuthActivity)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, loginFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToRegister() {
        val registerFragment = RegisterFragment().apply {
            setRegisterNavigationListener(this@AuthActivity)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, registerFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToLogin() {
        val loginFragment = LoginFragment().apply {
            setLoginNavigationListener(this@AuthActivity)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, loginFragment)
            .addToBackStack(null)
            .commit()
    }
}
