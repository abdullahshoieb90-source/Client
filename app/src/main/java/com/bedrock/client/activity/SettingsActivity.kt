
package com.bedrock.client.activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bedrock.client.fragment.SettingsFragment

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }
}
