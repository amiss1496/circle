package com.example.circle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_pass.*

class ForgotPass : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        forgotBtn.setOnClickListener {
            var email = forgotEmail.text.toString()
            val auth = FirebaseAuth.getInstance()
            forgotProgressBar.visibility = View.VISIBLE

            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {
                        forgotProgressBar.visibility = View.INVISIBLE
                        Toast.makeText(this, "An email has been sent to you.", Toast.LENGTH_SHORT).show()
                    } else {
                        forgotProgressBar.visibility = View.INVISIBLE
                        Toast.makeText(this, it.exception!!.message.toString(), Toast.LENGTH_LONG)
                            .show()
                    }

                }
            }

        }

    }
}
