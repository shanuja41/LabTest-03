package com.example.labtest3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity

class loginActivity : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val usernameField = findViewById<EditText>(R.id.enterusername)
        val passwordField = findViewById<EditText>(R.id.enterpassword)
        val loginbtn = findViewById<Button>(R.id.login_btn)

        loginbtn.setOnClickListener {
            val username = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (validateInputs(username, password)) {
                // Proceed to HomeActivity if validation is successful
                val intent = Intent(this, activity_dashboard::class.java)
                startActivity(intent)
                finish()
            }
        }

        val reSignup = findViewById<TextView>(R.id.redirect_Signup)
        reSignup.setOnClickListener {
            val intent = Intent(this, signupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateInputs(username: String, password: String): Boolean {
        var isValid = true

        if (username.isEmpty()) {
            findViewById<EditText>(R.id.enterusername).error = "Username is required"
            isValid = false
        }

        if (password.isEmpty()) {
            findViewById<EditText>(R.id.enterpassword).error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            findViewById<EditText>(R.id.enterpassword).error = "Password must be at least 6 characters"
            isValid = false
        }

        return isValid
    }
}
