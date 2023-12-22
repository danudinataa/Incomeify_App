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
import androidx.fragment.app.viewModels
import com.incomeify.incomeifyapp.R
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import com.incomeify.incomeifyapp.ui.dashboard.DashboardActivity
import com.incomeify.incomeifyapp.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.incomeify.incomeifyapp.data.session.SharedPreferencesManager
import com.incomeify.incomeifyapp.domain.viewmodel.LoginViewModel
import com.incomeify.incomeifyapp.ui.customview.CustomDialog


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        binding.loginButton.setOnClickListener {
            loginUser()
        }

        setupClickableText()
    }

    private fun loginUser() {
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()

        if (email.isBlank() || password.isBlank()) {
            CustomDialog(
                requireContext(),
                getString(R.string.error_login_input),
                R.raw.error_anim
            ).show()
        } else {
            viewModel.loginUser(email, password).observe(viewLifecycleOwner) { result ->
                result.onSuccess { loginResponse ->
                    loginResponse?.token?.let { token ->
                        sharedPreferencesManager.saveAuthToken(token)
                        viewModel.getUserData(token).observe(viewLifecycleOwner) { userDataResult ->
                            userDataResult.onSuccess { userData ->
                                userData?.let {
                                    sharedPreferencesManager.saveUsername(userData.name.toString())
                                    sharedPreferencesManager.saveEmail(userData.email.toString())

                                    navigateToDashboard()
                                }
                            }.onFailure { _ ->
                                // Handle failure to get user data
                            }
                        }
                    }
                }.onFailure { error ->
                    Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                }
            }
        }


    }

    private fun setupClickableText() {
        val spannable = SpannableString("Don't have account? Register Here").apply {
            val start = indexOf("Register Here")
            val end = start + "Register Here".length
            setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    navigationListener?.navigateToRegister()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.iguana_green)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.registerText.text = spannable
        binding.registerText.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun navigateToDashboard() {
        val intent = Intent(requireActivity(), DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    interface LoginNavigationListener {
        fun navigateToRegister()
    }

    private var navigationListener: LoginNavigationListener? = null

    fun setLoginNavigationListener(listener: LoginNavigationListener) {
        navigationListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
