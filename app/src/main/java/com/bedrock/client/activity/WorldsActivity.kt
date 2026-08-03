
package com.bedrock.client.activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bedrock.client.worlds.WorldManager

class WorldsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WorldManager.getInstance(this).loadWorlds()
    }
}
