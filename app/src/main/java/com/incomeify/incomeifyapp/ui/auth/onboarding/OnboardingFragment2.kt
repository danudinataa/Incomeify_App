package com.incomeify.incomeifyapp.ui.auth.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.incomeify.incomeifyapp.R
import android.widget.Button

class OnboardingFragment2 : Fragment() {

    private var listener: OnboardingNavigationListener? = null

    interface OnboardingNavigationListener {
        fun onNextClicked()
        fun onBackClicked()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = view.findViewById<Button>(R.id.buttonOnboardingNext)
        buttonNext.setOnClickListener {
            listener?.onNextClicked()
        }

        val buttonBack = view.findViewById<Button>(R.id.buttonOnboardingBack)
        buttonBack.setOnClickListener {
            listener?.onBackClicked()
        }

    }

    fun setOnboardingNavigationListener(listener: OnboardingNavigationListener) {
        this.listener = listener
    }
}
