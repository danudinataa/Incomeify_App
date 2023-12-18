package com.incomeify.incomeifyapp.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextWatcher
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.method.LinkMovementMethod
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.incomeify.incomeifyapp.R
import com.incomeify.incomeifyapp.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle Register button click
        binding.registerButton.setOnClickListener {
            registerUser()
        }

        setupClickableLoginText()
        setupPasswordConfirmationWatcher()
    }

    private fun setupClickableLoginText() {
        val spannable = SpannableString("Already have an account? Login").apply {
            val start = indexOf("Login")
            val end = start + "Login".length
            setSpan(object : NoUnderlineSpan() {
                override fun onClick(widget: View) {
                    navigateToLogin()
                }
            }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.iguana_green)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.loginText.text = spannable
        binding.loginText.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setupPasswordConfirmationWatcher() {
        binding.confirmPasswordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validatePasswords()
            }
        })
    }

    private fun validatePasswords() {
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()
        if (password != confirmPassword) {
            binding.confirmPasswordInput.error = "Passwords do not match"
        } else {
            binding.confirmPasswordInput.error = null
        }
    }

    private fun registerUser() {
        val name = binding.nameInput.text.toString()
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()

        if (password == confirmPassword) {
            // Implement your registration logic here
        } else {
            binding.confirmPasswordInput.error = "Passwords do not match"
        }
    }

    interface RegisterNavigationListener {
        fun navigateToLogin()
    }

    private var navigationListener: RegisterNavigationListener? = null

    fun setRegisterNavigationListener(listener: RegisterNavigationListener) {
        navigationListener = listener
    }

    private fun navigateToLogin() {
        navigationListener?.navigateToLogin()
    }

    abstract class NoUnderlineSpan : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
