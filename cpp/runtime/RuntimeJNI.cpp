Enter#include <jni.h>
#include <string>

#include "RuntimeConfig.h"

using namespace bedrock::runtime;

static std::string jstringToString(JNIEnv* env, jstring value) {
    if (value == nullptr) {
        return "";
    }

    const char* chars = env->GetStringUTFChars(value, nullptr);
    std::string result(chars);
    env->ReleaseStringUTFChars(value, chars);

    return result;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_bedrock_client_runtime_RuntimeManager_nativeSetRuntimeConfig(
        JNIEnv* env,
        jobject /* thiz */,
        jstring packageName,
        jstring apkPath,
        jstring nativeLibraryDir,
        jstring sandboxPath) {

    RuntimeConfig& config = RuntimeConfig::getInstance();

    config.setMinecraftPackage(
        jstringToString(env, packageName));

    config.setApkPath(
        jstringToString(env, apkPath));

    config.setNativeLibraryDir(
        jstringToString(env, nativeLibraryDir));

    config.setSandboxPath(
        jstringToString(env, sandboxPath));
}
