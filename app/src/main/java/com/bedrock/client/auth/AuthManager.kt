
package com.bedrock.client.auth

class AuthManager {
    data class Tokens(val accessToken: String, val refreshToken: String, val expiresIn: Long)

    fun authenticateWithMicrosoft(): Tokens {
        // OAuth flow placeholder
        throw NotImplementedError("Microsoft OAuth needs UI")
    }

    fun refresh(refreshToken: String): Tokens {
        // Refresh Token logic
        return Tokens("new_access", refreshToken, 3600)
    }

    fun validate(token: String): Boolean = token.isNotEmpty()
}
