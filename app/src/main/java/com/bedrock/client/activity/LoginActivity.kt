
package com.bedrock.client.activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bedrock.client.account.AccountManager
import com.bedrock.client.auth.AuthManager

class LoginActivity : AppCompatActivity() {
    private val authManager = AuthManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Implement login UI with Microsoft OAuth
    }
}
