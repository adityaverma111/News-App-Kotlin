package com.example.assignments.Authentication

import android.content.Context
import android.content.Intent
import com.example.assignments.ui.fragments.ProfileFragment

class SharePrefrence(context: Context) {

    companion object {
        private const val PREFERENCES_FILE_KEY = "com.example.userprefs"
        private const val USERNAME_KEY = "username"
        private const val PASSWORD_KEY = "password"
        private const val IS_LOGGED_IN = "is_logged_in"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

    fun saveUserCredentials(username: String, password: String) {
        sharedPreferences.edit().apply {
            putString(USERNAME_KEY, username)
            putString(PASSWORD_KEY, password)
            apply()
        }
    }

    fun getUsername() = sharedPreferences.getString(USERNAME_KEY, null)
    fun getPassword() = sharedPreferences.getString(PASSWORD_KEY, null)

    fun isLoggedIn() = sharedPreferences.getBoolean(IS_LOGGED_IN, false)

    fun setLoggedIn(loggedIn: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(IS_LOGGED_IN, loggedIn)
            apply()
        }
    }
    fun saveLoginDetails(context: Context, username: String, password: String) {
        val sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("username", username)
            putString("password", password)
            apply()
        }
    }


    fun logoutUser(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("isLoggedIn")
        editor.apply()

        // Intent to navigate back to the login screen
        val loginIntent = Intent(context, login::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(loginIntent)
    }

}
