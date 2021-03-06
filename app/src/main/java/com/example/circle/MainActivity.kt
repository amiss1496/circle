package com.example.circle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.circle.Fragments.profile.ProfileFragment
import com.example.circle.Fragments.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
           /*R.id.nav_home ->{
                moveToFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_add_post ->{
                //moveToFragment(())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_notifications ->{
                moveToFragment(NotificationsFragment())
                return@OnNavigationItemSelectedListener true
            }*/
            R.id.nav_profile ->{
                val intent = Intent(this,Profile::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
//                moveToFragment(ProfileFragment())
//                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_search ->{
                moveToFragment(SearchFragment())
                return@OnNavigationItemSelectedListener true
            }

        }


        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.dd)
        button1.setOnClickListener {
            var intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(ProfileFragment())
    }
    private fun moveToFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container,fragment)
        fragmentTransition.commit()

    }
}
