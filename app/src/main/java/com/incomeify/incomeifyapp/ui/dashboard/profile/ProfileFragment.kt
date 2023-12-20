package com.incomeify.incomeifyapp.ui.dashboard.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.incomeify.incomeifyapp.R
import com.incomeify.incomeifyapp.databinding.FragmentProfileBinding
import android.widget.Button
import com.incomeify.incomeifyapp.data.session.SharedPreferencesManager
import com.incomeify.incomeifyapp.ui.auth.AuthActivity
import androidx.appcompat.app.AlertDialog




class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogout = view.findViewById<Button>(R.id.button)
        btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }

    }

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

        with(binding) {
            // TODO: binding username & email with acc
            tvUsername.text = "USERNAME"
            tvEmail.text = "user@gmail.com"

            btnChangeLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
    private fun logoutUser() {
        val sharedPreferencesManager = SharedPreferencesManager(requireContext())
        sharedPreferencesManager.clearAuthToken()

        val intent = Intent(activity, AuthActivity::class.java)
        intent.putExtra("directLogin", true)
        startActivity(intent)
        activity?.finish()
    }


    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.confirm_logout))
            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                logoutUser()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        }
    }
}