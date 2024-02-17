package com.example.assignments.Authentication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.assignments.R
import com.example.assignments.ui.HomeActivity

class login : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginButton: Button = findViewById(R.id.loginButton)

        val register = findViewById<TextView>(R.id.register)

       

        // Set an OnClickListener on the button
        register.setOnClickListener {
            // Create an Intent to start the new activity
            val intent = Intent(this, signup::class.java)

            // Start the new activity
            startActivity(intent)
        }



        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val prefs = SharePrefrence(this)

            if (username == prefs.getUsername() && password == prefs.getPassword()) {
                prefs.setLoggedIn(true)
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please Register", Toast.LENGTH_LONG).show()
            }
        }
    }
}