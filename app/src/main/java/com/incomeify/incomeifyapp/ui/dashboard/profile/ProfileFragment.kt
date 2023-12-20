package com.incomeify.incomeifyapp.ui.dashboard.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.incomeify.incomeifyapp.R
import com.incomeify.incomeifyapp.data.session.SharedPreferencesManager
import com.incomeify.incomeifyapp.databinding.FragmentProfileBinding
import com.incomeify.incomeifyapp.ui.auth.AuthActivity


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        with(binding) {
            val username = sharedPreferencesManager.getUsername()
            val email = sharedPreferencesManager.getEmail()

            tvUsername.text = username ?: "Unknown"
            tvEmail.text = email ?: "Unknown"

            btnChangeLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }

            btnLogout.setOnClickListener {
                showLogoutDialog()
            }
        }
    }

    private fun logoutUser() {
        sharedPreferencesManager.clearAuthToken()

        val intent = Intent(activity, AuthActivity::class.java)
        intent.putExtra("directLogin", true)
        startActivity(intent)
        activity?.finish()
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext(), R.style.CustomAlertDialogTheme)
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.confirm_logout))
            .setIcon(R.drawable.baseline_exit_to_app_24)
            .setPositiveButton(getString(R.string.logout)) { dialog, _ ->
                logoutUser()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
}
