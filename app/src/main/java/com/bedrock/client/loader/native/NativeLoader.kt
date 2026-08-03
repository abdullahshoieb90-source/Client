
package com.bedrock.client.loader.native
import com.bedrock.client.logger.Logger

class NativeLoader {
    fun loadLibrary(name: String) {
        try { System.loadLibrary(name) } catch (e: UnsatisfiedLinkError) { Logger.e("NativeLoader", "Failed to load $name", e) }
    }
    fun loadFromPath(path: String) {
        try { System.load(path) } catch (e: UnsatisfiedLinkError) { Logger.e("NativeLoader", "Failed to load $path", e) }
    }
}
