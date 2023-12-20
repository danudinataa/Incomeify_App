package com.incomeify.incomeifyapp.ui.auth.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.incomeify.incomeifyapp.R
import android.widget.Button
import android.widget.LinearLayout

class OnboardingFragment : Fragment() {

    private var listener: OnboardingNavigationListener? = null

    interface OnboardingNavigationListener {
        fun onNextClicked()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = view.findViewById<Button>(R.id.buttonOnboardingNext)
        buttonNext.setOnClickListener {
            listener?.onNextClicked()
        }

        initializeIndicatorDots(view)
    }

    private fun initializeIndicatorDots(view: View) {
        val dotsLayout = view.findViewById<LinearLayout>(R.id.indicatorDots)
        dotsLayout.removeAllViews()
        val dots = arrayOfNulls<View>(3)

        val dotSize = resources.getDimensionPixelSize(R.dimen.dot_size)
        val dotMargin = resources.getDimensionPixelSize(R.dimen.dot_margin)

        for (i in dots.indices) {
            dots[i] = View(context).apply {
                layoutParams = LinearLayout.LayoutParams(dotSize, dotSize).also {
                    it.setMargins(if (i == 0) 0 else dotMargin, 0, 0, 0)
                }
                setBackgroundResource(R.drawable.dot_selector)
                isSelected = i == 0 // Set the first dot as selected or based on the current page
            }
            dotsLayout.addView(dots[i])
        }
    }

    fun setOnboardingNavigationListener(listener: OnboardingNavigationListener) {
        this.listener = listener
    }
}
