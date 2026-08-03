
package com.bedrock.client.security
import android.content.Context
import android.content.pm.ApplicationInfo

class SecurityManager private constructor(private val context: Context) {
    fun performChecks(): Report {
        val isRooted = checkRoot()
        val isDebuggable = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        val isTampered = checkSignature()
        return Report(isRooted, isDebuggable, isTampered)
    }

    private fun checkRoot(): Boolean {
        val paths = listOf("/system/xbin/su", "/system/bin/su", "/system/app/Superuser.apk")
        return paths.any { java.io.File(it).exists() }
    }

    private fun checkSignature(): Boolean { return false } // anti tamper placeholder

    data class Report(val isRooted: Boolean, val isDebuggable: Boolean, val isTampered: Boolean)

    companion object {
        @Volatile private var INSTANCE: SecurityManager? = null
        fun getInstance(ctx: Context) = INSTANCE ?: synchronized(this) { INSTANCE ?: SecurityManager(ctx).also { INSTANCE = it } }
    }
}
