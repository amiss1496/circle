package com.example.circle.Fragments.profile


//import com.innocyber.circle.AccountSettingsActivity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.circle.Models.User
import com.example.circle.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    private lateinit var profileId: String
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)
        if (pref != null){
            this.profileId = pref.getString("profileId","none")!!
        }

        if(profileId == firebaseUser.uid){

            view.edit_account_settings.text = "Edit Profile"
        }

        else if (profileId != firebaseUser.uid){
            checkFollowAndFollowing()
        }

        userInfo()
        return view
    }

    private fun checkFollowAndFollowing()
    {

    }
    private fun userInfo()
    {
        val userRef= FirebaseDatabase.getInstance().getReference("Users").child(profileId)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError)
            {
            }

            override fun onDataChange(p0: DataSnapshot)
            {
                if(context!=null)
                {
                    return
                }
                if (p0.exists())
                {
                    val user=p0.getValue<User>(User::class.java)
                    // Picasso.get().load(user!!.getImage().placeholder(R.drawable.profile).into(view?.profile_image))
                    view?.profile_fragment_username?.text=user!!.getUsername()
                    view?.full_name_profile_frag?.text=user!!.getFullname()
                    view?.bio_profile?.text=user!!.getBio()


                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId",firebaseUser.uid)
        pref?.apply()
    }

    override fun onPause() {
        super.onPause()
        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId",firebaseUser.uid)
        pref?.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId",firebaseUser.uid)
        pref?.apply()
    }
}

