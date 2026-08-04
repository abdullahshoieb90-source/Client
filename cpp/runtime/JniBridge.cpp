#include "JniBridge.h"

#include <string>

#include "../logger/logger.h"
#include "runtime.h"

using namespace bedrock::runtime;

static std::string jstringToString(JNIEnv* env, jstring value) {

    if (value == nullptr)
        return "";

    const char* chars = env->GetStringUTFChars(value, nullptr);

    std::string result(chars);

    env->ReleaseStringUTFChars(value, chars);

    return result;
}

JNIEXPORT jboolean JNICALL
Java_com_vclient_runtime_NativeBridge_initializeRuntime(
        JNIEnv* env,
        jobject thiz,
        jstring apkPath,
        jstring nativeLibraryDir,
        jstring sandboxDir) {

    Runtime& runtime = Runtime::getInstance();

    if (!runtime.initialize(env, thiz))
        return JNI_FALSE;

    runtime.prepareEnvironment(
            jstringToString(env, sandboxDir));

    LOGI("JNI", "Runtime initialized");

    return JNI_TRUE;
}

JNIEXPORT jboolean JNICALL
Java_com_vclient_runtime_NativeBridge_loadMinecraft(
        JNIEnv* env,
        jobject thiz) {

    return Runtime::getInstance()
            .loadMinecraftLibraries()
            ? JNI_TRUE
            : JNI_FALSE;
}

JNIEXPORT jboolean JNICALL
Java_com_vclient_runtime_NativeBridge_startMinecraft(
        JNIEnv* env,
        jobject thiz) {

    return Runtime::getInstance()
            .start()
            ? JNI_TRUE
            : JNI_FALSE;
}

JNIEXPORT void JNICALL
Java_com_vclient_runtime_NativeBridge_shutdownRuntime(
        JNIEnv* env,
        jobject thiz) {

    Runtime::getInstance().shutdown();
}
