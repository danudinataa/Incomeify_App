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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.incomeify.incomeifyapp.domain.model.RequestGoogle
import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.incomeify.incomeifyapp.data.session.SharedPreferencesManager
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

        initializeGoogleSignIn()

        binding.loginButton.setOnClickListener {
            loginUser()
        }

        binding.googleSignInLayout.setOnClickListener {
            signInWithGoogle()
        }

        setupClickableText()
    }

    private fun initializeGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1040188149750-9gvng94psi0ia553fk5m1j8eial6vt9s.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
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

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("LoginFragment", "onActivityResult: ${result.resultCode}")
        Log.d("LoginFragment", "onActivityResult: ${result.data}")

        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        } else {
            // Check if there's any data in the result
            if (result.data != null) {
                // Log additional information from the Intent
                Log.d("LoginFragment", "onActivityResult: ${result.data!!.extras}")

                // Extract and log the contents of the extras bundle
                val extras = result.data!!.extras
                if (extras != null) {
                    val keySet = extras.keySet()
                    for (key in keySet) {
                        Log.d("LoginFragment", "onActivityResult - Extra: $key - Value: ${extras.get(key)}")
                    }
                }
            }

            Log.d("LoginFragment", "Google Sign-In failed or was cancelled")
        }
    }

    private fun signInWithGoogle() {
        Log.d("LoginFragment", "signInWithGoogle called")
        val signInIntent = googleSignInClient.signInIntent
        Log.d("LoginFragment", "signInWithGoogle - Intent: $signInIntent")
        googleSignInLauncher.launch(signInIntent)
        navigateToDashboard()
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account?.idToken

            if (idToken != null) {
                val requestGoogle = RequestGoogle(token = idToken)

                viewModel.googleLogin(requestGoogle).observe(viewLifecycleOwner) { result ->
                    result.onSuccess {
                        Log.d("LoginFragment", "Login success")
                        navigateToDashboard()
                    }.onFailure { error ->
                        Log.d("LoginFragment", "Login failed: ${error.message}")
                        Snackbar.make(requireView(), error.message ?: "Unknown error", Snackbar.LENGTH_LONG).show()
                        Log.e("LoginFragment", "Google Sign-In failed: ${error.message}")
                        Snackbar.make(requireView(), "Google Sign-In failed: ${error.message}", Snackbar.LENGTH_LONG).show()
                    }
                }
            } else {
                Log.d("LoginFragment", "idToken is null")
            }
        } catch (e: ApiException) {
            Log.e("LoginFragment", "Google Sign-In failed: ${e.message}")
            e.printStackTrace() // Add this line to print the stack trace
            Snackbar.make(requireView(), "Google Sign-In failed: ${e.message}", Snackbar.LENGTH_LONG).show()
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
