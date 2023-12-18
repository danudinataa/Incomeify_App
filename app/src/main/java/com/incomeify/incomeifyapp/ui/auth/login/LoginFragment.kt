package com.incomeify.incomeifyapp.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.incomeify.incomeifyapp.R
import com.incomeify.incomeifyapp.databinding.FragmentLoginBinding
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import com.incomeify.incomeifyapp.ui.dashboard.DashboardActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle Login button click
        binding.loginButton.setOnClickListener {
            val intent = Intent(requireActivity(), DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Handle Google sign-in click
        binding.googleSignInLayout.setOnClickListener {
            // Handle Google sign-in logic here
        }

        val spannable = SpannableString("Don't have account? Register Here").apply {
            val start = indexOf("Register Here")
            val end = start + "Register Here".length
            setSpan(object : NoUnderlineSpan() {
                override fun onClick(widget: View) {
                    // Navigate to RegisterFragment
                    navigateToRegister()
                }
            }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.iguana_green)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.registerText.text = spannable
        binding.registerText.movementMethod = LinkMovementMethod.getInstance()
    }


    interface LoginNavigationListener {
        fun navigateToRegister()
    }

    private var navigationListener: LoginNavigationListener? = null

    fun setLoginNavigationListener(listener: LoginNavigationListener) {
        navigationListener = listener
    }

    private fun navigateToRegister() {
        navigationListener?.navigateToRegister()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract class NoUnderlineSpan : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }
    }

}
