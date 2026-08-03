
package com.bedrock.client.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bedrock.client.R
import com.bedrock.client.fragment.HomeFragment
import com.bedrock.client.fragment.ModsFragment
import com.bedrock.client.fragment.ProfileFragment
import com.bedrock.client.fragment.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        nav.setOnItemSelectedListener {
            val frag = when(it.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_mods -> ModsFragment()
                R.id.nav_profile -> ProfileFragment()
                R.id.nav_settings -> SettingsFragment()
                else -> HomeFragment()
            }
            switchFragment(frag)
            true
        }
        if (savedInstanceState == null) switchFragment(HomeFragment())
    }
    private fun switchFragment(f: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit()
    }
}
