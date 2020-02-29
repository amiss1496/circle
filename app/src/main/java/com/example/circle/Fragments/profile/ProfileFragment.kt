package com.example.circle.Fragments.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.circle.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        (R.layout.fragment_profile)
        var readData=ReadData()
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var ref: DatabaseReference = database.getReference("Users")
        ref.addValueEventListener(readData)

    }
    inner class ReadData:ValueEventListener
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
//        firebaseUser = FirebaseAuth.getInstance().currentUser!!
//        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)
//        if (pref != null){
//            this.profileId = pref.getString("profileId","none")!!
//        }
//
//        if(profileId == firebaseUser.uid){
//
//            view.edit_account_settings.text = "Edit Profile"
//        }
//
//        else if (profileId != firebaseUser.uid){
//            checkFollowAndFollowing()
//        }
//
//        userInfo()
//        return view
//    }
//
//    private fun checkFollowAndFollowing()
//    {
//
//    }
//    private fun userInfo()
//    {
//        val userRef= FirebaseDatabase.getInstance().getReference("Users").child(profileId)
//        userRef.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError)
//            {
//            }
//
//            override fun onDataChange(p0: DataSnapshot)
//            {
//                if(context!=null)
//                {
//                    return
//                }
//                if (p0.exists())
//                {
//                    val user=p0.getValue<User>(User::class.java)
//                    // Picasso.get().load(user!!.getImage().placeholder(R.drawable.profile).into(view?.profile_image))
//                    view?.profile_fragment_username?.text=user?.getUsername()
//                    view?.full_name_profile_frag?.text= user?.getFullname()
//                    view?.bio_profile?.text=user!!.getBio()
//                    Toas()
//
//
//                }
//            }
//        })
//    }
//
//    override fun onStop() {
//        super.onStop()
//        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)?.edit()
//        pref?.putString("profileId",firebaseUser.uid)
//        pref?.apply()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)?.edit()
//        pref?.putString("profileId",firebaseUser.uid)
//        pref?.apply()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)?.edit()
//        pref?.putString("profileId",firebaseUser.uid)
//        pref?.apply()
//    }
//
//    override fun onStart() {
//        super.onStart()
//
//    }


