
#include "bridge.h"
#include "../logger/logger.h"
#include "../bootstrap/bootstrap.h"
#include <android/log.h>

namespace bedrock::bridge {

Bridge& Bridge::getInstance() {
    static Bridge instance;
    return instance;
}

bool Bridge::initialize(JNIEnv* env) {
    env->GetJavaVM(&jvm);
    LOGI("Bridge", "JVM attached: %p", jvm);

    // Find BridgeManager class
    jclass localClass = env->FindClass("com/bedrock/client/bridge/BridgeManager");
    if (!localClass) {
        LOGE("Bridge", "Failed to find BridgeManager class");
        return false;
    }
    bridgeManagerClass = (jclass)env->NewGlobalRef(localClass);
    env->DeleteLocalRef(localClass);
    return true;
}

JNIEnv* Bridge::getEnv() {
    JNIEnv* env = nullptr;
    if (jvm) jvm->AttachCurrentThread(&env, nullptr);
    return env;
}

void Bridge::attachActivity(JNIEnv* env, jobject activity) {
    if (activityRef) env->DeleteGlobalRef(activityRef);
    activityRef = env->NewGlobalRef(activity);
    LOGI("Bridge", "Activity attached");
}

void Bridge::detachActivity() {
    JNIEnv* env = getEnv();
    if (env && activityRef) {
        env->DeleteGlobalRef(activityRef);
        activityRef = nullptr;
    }
}

int Bridge::launchGame(const std::string& instancePath, const std::string& version) {
    auto& boot = bootstrap::Bootstrap::getInstance();
    bool ok = boot.launchMinecraft(instancePath, version);
    return ok ? 12345 : -1; // fake PID for Java side
}

void Bridge::notifyModuleState(const std::string& name, bool enabled) {
    JNIEnv* env = getEnv();
    if (!env || !bridgeManagerClass) return;
    // Call Java callback if needed
}

void Bridge::logToJava(int level, const std::string& tag, const std::string& msg) {
    // Forward to Java Logger via Timber if needed
    LOGI(tag.c_str(), "%s", msg.c_str());
}

void Bridge::callJavaMethod(const std::string& method, const std::string& args) {
    JNIEnv* env = getEnv();
    if (!env || !bridgeManagerClass || !bridgeManagerRef) return;

    jmethodID mid = nullptr;

    auto it = methodCache.find(method);
    if (it != methodCache.end()) {
        mid = it->second;
    } else {
        mid = env->GetMethodID(bridgeManagerClass, method.c_str(), "(Ljava/lang/String;)V");
        if (mid) {
            methodCache[method] = mid;
        }
    }

    if (!mid) return;

    jstring jArgs = env->NewStringUTF(args.c_str());
    env->CallVoidMethod(bridgeManagerRef, mid, jArgs);
    env->DeleteLocalRef(jArgs);
}

// JNI Exports called from Kotlin BridgeManager
extern "C" {

JNIEXPORT void JNICALL Java_com_bedrock_client_bridge_BridgeManager_nativeInit(JNIEnv* env, jobject thiz) {
    Bridge::getInstance().initialize(env);
}

JNIEXPORT void JNICALL Java_com_bedrock_client_bridge_BridgeManager_nativeAttachActivity(JNIEnv* env, jobject thiz, jobject activity) {
    Bridge::getInstance().attachActivity(env, activity);
}

JNIEXPORT void JNICALL Java_com_bedrock_client_bridge_BridgeManager_nativeDetachActivity(JNIEnv* env, jobject thiz) {
    Bridge::getInstance().detachActivity();
}

JNIEXPORT jobject JNICALL Java_com_bedrock_client_bridge_BridgeManager_nativeCall(JNIEnv* env, jobject thiz, jstring method, jobjectArray args) {
    return nullptr;
}

JNIEXPORT jint JNICALL Java_com_bedrock_client_bridge_BridgeManager_nativeLaunchGame(JNIEnv* env, jobject thiz, jstring instancePath, jstring version) {
    const char* cPath = env->GetStringUTFChars(instancePath, nullptr);
    const char* cVer = env->GetStringUTFChars(version, nullptr);
    int pid = Bridge::getInstance().launchGame(cPath, cVer);
    env->ReleaseStringUTFChars(instancePath, cPath);
    env->ReleaseStringUTFChars(version, cVer);
    return pid;
}

} // extern C

} // namespace
