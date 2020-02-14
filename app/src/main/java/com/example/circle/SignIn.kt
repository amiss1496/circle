package com.example.circle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        toSignup.setOnClickListener {
            var intent = Intent(this@SignIn,SignUp::class.java)
            startActivity(intent)
        }

        forgotPass.setOnClickListener {
            var intent = Intent(this@SignIn, ForgotPass::class.java)
            startActivity(intent)
        }

        btnSignin.setOnClickListener {
            loginUser()
        }
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null && FirebaseAuth.getInstance().currentUser!!.isEmailVerified) {
            val intent = Intent(this@SignIn, MainActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun loginUser() {
        var uEmail = inputEmail_signin.text.toString().trim()
        var uPass = inputPass.text.toString().trim()
        auth = FirebaseAuth.getInstance()

        if (uEmail.isNotEmpty() && uPass.isNotEmpty()){
            progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(uEmail,uPass).addOnCompleteListener {
                if(it.isSuccessful){
                    if(auth.currentUser!!.isEmailVerified()){
                        progressBar.visibility = View.INVISIBLE
                        val intent= Intent(this,MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK )
                        startActivity(intent)
                    }else{
                        progressBar.visibility = View.INVISIBLE
                        Toast.makeText(this@SignIn,"Email isn't verified",Toast.LENGTH_LONG).show()
                    }
                }else{
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@SignIn,"Incorrect email or password",Toast.LENGTH_LONG).show()
                }
            }
        }else{
            Toast.makeText(this@SignIn,"All fields are required",Toast.LENGTH_LONG).show()
        }
    }
}
