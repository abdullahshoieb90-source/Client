Enter#pragma once

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jboolean JNICALL
Java_com_vclient_runtime_NativeBridge_initializeRuntime(
        JNIEnv* env,
        jobject thiz,
        jstring apkPath,
        jstring nativeLibraryDir,
        jstring sandboxDir);

JNIEXPORT jboolean JNICALL
Java_com_vclient_runtime_NativeBridge_loadMinecraft(
        JNIEnv* env,
        jobject thiz);

JNIEXPORT jboolean JNICALL
Java_com_vclient_runtime_NativeBridge_startMinecraft(
        JNIEnv* env,
        jobject thiz);

JNIEXPORT void JNICALL
Java_com_vclient_runtime_NativeBridge_shutdownRuntime(
        JNIEnv* env,
        jobject thiz);

#ifdef __cplusplus
}
#endif
