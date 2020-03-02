package com.example.circle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*

class Profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        edit_account_settings.setOnClickListener {
            val intent = Intent(this, AccountSettingsActivity::class.java)
            startActivity(intent)
        }
        var readData=ReadData()
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var ref: DatabaseReference = database.getReference("Users")
        ref.addValueEventListener(readData)

    }
    inner class ReadData: ValueEventListener
    {
        override fun onDataChange(p0: DataSnapshot) {
            var username:String=p0?.getValue(String::class.java)!!
            profile_fragment_username.text=username

        }

        override fun onCancelled(p0: DatabaseError) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
