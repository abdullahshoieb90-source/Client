
#include <jni.h>
#include "../logger/logger.h"
#include "../bootstrap/bootstrap.h"
#include "../bridge/bridge.h"

// جميع ملفات JNI:
// JNI_OnLoad()
// Native Methods
// Java Bindings

extern "C" {

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env = nullptr;
    if (vm->GetEnv((void**)&env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    bedrock::logger::Logger::init();
    LOGI("JNI", "JNI_OnLoad called - V Client native library loading");

    // Initialize Bootstrap via Bridge
    bedrock::bridge::Bridge::getInstance().initialize(env);

    return JNI_VERSION_1_6;
}

JNIEXPORT void JNI_OnUnload(JavaVM* vm, void* reserved) {
    LOGI("JNI", "JNI_OnUnload");
    bedrock::bootstrap::Bootstrap::getInstance().shutdown();
}

} // extern C
