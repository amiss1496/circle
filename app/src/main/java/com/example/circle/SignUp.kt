package com.example.circle
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.circle.Models.User
import com.example.circle.R
import com.example.circle.SignIn
import com.example.circle.domains
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUp : AppCompatActivity(){
    var listOfAll = domains.joinToString(separator="|")
    val USERNAME = Pattern.compile(
        "[a-zA-Z0-9_]{3,16}"
    )
   val EMAIL = Pattern.compile(
        "[a-zA-Z0-9-.]{6,30}@(?:${listOfAll})\\b"
    )
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnTosignin.setOnClickListener {
            var intent = Intent(this@SignUp, SignIn::class.java)
            startActivity(intent)
        }

        btnSignup.setOnClickListener {
            var uEmail = inputEmail_signup.text.toString().trim()
            var uFullname = inputFullname_signup.text.toString()
            var uName = inputUsername_input.text.toString().trim()
            var uPass = inputPass_signup.text.toString().trim()

            if(uEmail.isNotEmpty() &&  uFullname.isNotEmpty()&& uName.isNotEmpty() && uPass.isNotEmpty()){

                progressBarSignUp.visibility = View.VISIBLE

                if(!EMAIL.matcher(uEmail).matches()){
                    progressBarSignUp.visibility = View.INVISIBLE
                    inputEmail_signup.error = "Invalid email"
                    inputEmail_signup.requestFocus()
                    progressBarSignUp.visibility = View.INVISIBLE
                    return@setOnClickListener
                }
                if(!USERNAME.matcher(uName).matches()){
                    progressBarSignUp.visibility = View.INVISIBLE
                    inputUsername_input.error = "Invalid username"
                    inputUsername_input.requestFocus()
                    progressBarSignUp.visibility = View.INVISIBLE
                    return@setOnClickListener
                }
                if(uPass.length <8){
                    Toast.makeText(this@SignUp,"password must be 8 chars or more",Toast.LENGTH_SHORT).show()
                    inputPass_signup.requestFocus()
                    progressBarSignUp.visibility = View.INVISIBLE
                    return@setOnClickListener
                }

                createUser(uName,uFullname,uEmail,uPass)
            }else{
                Toast.makeText(this@SignUp,"All fields required",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun createUser(uName:String,uFullname:String,uEmail:String,uPass:String) {
        auth = FirebaseAuth.getInstance()
        var refUsername = FirebaseDatabase.getInstance().reference.child("users").orderByChild("username").equalTo(uName)
        val currentUserId = auth.currentUser?.uid
        val usersRef = FirebaseDatabase.getInstance().reference.child("users")

        refUsername.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.childrenCount>0){
                    Toast.makeText(this@SignUp, "username is already taken!", Toast.LENGTH_SHORT).show()
                }else{
                    auth.createUserWithEmailAndPassword(uEmail, uPass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            auth.currentUser?.sendEmailVerification()?.addOnCompleteListener{
                                if(it.isSuccessful){
                                    val user = User(uName,uFullname,uEmail,"","")
                                    usersRef.child(currentUserId!!).setValue(user).addOnCompleteListener {
                                        if(it.isSuccessful){
                                            Toast.makeText(this@SignUp, "account created, verification link was send to you.", Toast.LENGTH_LONG).show()
                                            val intent = Intent(this@SignUp, SignIn::class.java)
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                            btnSignup.isEnabled = false
                                            startActivity(intent)
                                        }else{
                                            Toast.makeText(baseContext, "${it.exception!!.message.toString()}", Toast.LENGTH_SHORT).show()
//                                             auth.signOut()
                                        }
                                    }
                                }else{
                                    Toast.makeText(baseContext, "${it.exception!!.message.toString()}", Toast.LENGTH_SHORT).show()
//                                     auth.signOut()
                                }
                            }
                        } else {
                            Toast.makeText(baseContext, "${it.exception!!.message.toString()}", Toast.LENGTH_SHORT).show()
                            //                             auth.signOut()
                        }
                    }
                }
                progressBarSignUp.visibility = View.INVISIBLE
            }

        })

    }
}
