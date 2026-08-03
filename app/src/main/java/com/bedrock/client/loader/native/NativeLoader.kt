package com.bedrock.client.loader.native

import com.bedrock.client.logger.Logger

class NativeLoader {
    fun loadLibrary(name: String): Boolean = try {
        System.loadLibrary(name)
        true
    } catch (error: UnsatisfiedLinkError) {
        Logger.e("NativeLoader", "Failed to load $name", error)
        false
    } catch (error: SecurityException) {
        Logger.e("NativeLoader", "Not allowed to load $name", error)
        false
    }

    fun loadFromPath(path: String): Boolean = try {
        System.load(path)
        true
    } catch (error: UnsatisfiedLinkError) {
        Logger.e("NativeLoader", "Failed to load $path", error)
        false
    } catch (error: SecurityException) {
        Logger.e("NativeLoader", "Not allowed to load $path", error)
        false
    }
}
