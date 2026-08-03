
package com.bedrock.client.minecraft.runtime
import android.content.Context
import com.bedrock.client.bridge.BridgeManager

class MinecraftRuntime(private val context: Context) {
    fun start() {
        BridgeManager.getInstance().callNative("startRuntime", emptyArray())
    }
}
