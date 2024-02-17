package com.example.assignments.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.assignments.Authentication.SharePrefrence
import com.example.assignments.Authentication.login
import com.example.assignments.R


class ProfileFragment : Fragment(R.layout.fragment_profile) {



    lateinit var session: SharePrefrence

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assuming you have these TextViews in your fragment's layout
        val usernameTextView: TextView = view.findViewById(R.id.usernameTextView)
        val passwordTextView: TextView = view.findViewById(R.id.passwordTextView)
        val logOut: Button  = view.findViewById(R.id.logoutButton)


        // Set an OnClickListener on the button
        logOut.setOnClickListener {
            logoutUser(this)
        }

        // Initialize your UserPreferences class
        val userPreferences = SharePrefrence(requireContext())

        // Retrieve the username and password from SharedPreferences
        val username = userPreferences.getUsername() ?: "No Username Found"
        val password = userPreferences.getPassword() ?: "No Password Found"

        // Display the retrieved data in the TextViews
        usernameTextView.text = getString(R.string.display_username, username)
        passwordTextView.text = getString(R.string.display_password, password)



    }
    fun logoutUser(profileFragment: ProfileFragment) {
        // Getting SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("YourPreferenceName", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Clearing all data from SharedPreferences
        editor.clear()
        editor.apply()

        // Navigate back to LoginActivity
        val intent = Intent(activity, login::class.java)
        // Clearing the task stack and starting new task (optional, but recommended to prevent going back to the authenticated activity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        // Optionally, if you want to add some transition animation or need to perform additional cleanup, do it here
        // e.g., activity?.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }
}
