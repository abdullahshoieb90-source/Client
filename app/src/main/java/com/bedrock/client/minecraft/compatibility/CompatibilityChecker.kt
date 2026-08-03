
package com.bedrock.client.minecraft.compatibility
import android.os.Build

class CompatibilityChecker {
    fun isArm64(): Boolean = Build.SUPPORTED_ABIS.contains("arm64-v8a")
    fun isSupportedVersion(v: String): Boolean = true
    fun checkAll(): Report {
        return Report(isArm64(), Build.VERSION.SDK_INT >= 26)
    }
    data class Report(val isArm64: Boolean, val isSdkOk: Boolean) { val isCompatible get() = isArm64 && isSdkOk }
}
