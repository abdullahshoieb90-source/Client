
#include "esp.h"
#include "../module_manager.h"
#include <jni.h>
#include "../../logger/logger.h"
namespace bedrock::modules {
void ESP::onEnable() { enabled=true; LOGI("ESP","Enabled"); }
void ESP::onDisable() { enabled=false; LOGI("ESP","Disabled"); }
}
extern "C" {
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_ESPModule_nativeEnable(JNIEnv* env, jobject thiz){ bedrock::modules::ModuleManager::getInstance().setEnabled("ESP", true); }
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_ESPModule_nativeDisable(JNIEnv* env, jobject thiz){ bedrock::modules::ModuleManager::getInstance().setEnabled("ESP", false); }
}
