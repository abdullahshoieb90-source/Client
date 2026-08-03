
#include "fullbright.h"
#include "../module_manager.h"
#include <jni.h>
#include "../../logger/logger.h"

namespace bedrock::modules {
void Fullbright::onEnable() { enabled = true; LOGI("Fullbright", "Enabled - gamma patched"); }
void Fullbright::onDisable() { enabled = false; LOGI("Fullbright", "Disabled"); }
}

extern "C" {
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_FullbrightModule_nativeEnable(JNIEnv* env, jobject thiz) {
    bedrock::modules::ModuleManager::getInstance().setEnabled("Fullbright", true);
}
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_FullbrightModule_nativeDisable(JNIEnv* env, jobject thiz) {
    bedrock::modules::ModuleManager::getInstance().setEnabled("Fullbright", false);
}
}
