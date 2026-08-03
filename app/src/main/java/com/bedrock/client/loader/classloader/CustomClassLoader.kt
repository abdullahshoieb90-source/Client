
package com.bedrock.client.loader.classloader

class CustomClassLoader(parent: ClassLoader) : ClassLoader(parent) {
    fun defineClass(name: String, bytes: ByteArray): Class<*> = defineClass(name, bytes, 0, bytes.size)
}
