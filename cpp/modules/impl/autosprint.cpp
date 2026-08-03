
#include "autosprint.h"
#include "../module_manager.h"
#include <jni.h>
#include "../../logger/logger.h"
namespace bedrock::modules {
void AutoSprint::onEnable(){ enabled=true; LOGI("AutoSprint","Enabled"); }
void AutoSprint::onDisable(){ enabled=false; }
}
extern "C" {
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_AutoSprintModule_nativeEnable(JNIEnv* env, jobject thiz){ bedrock::modules::ModuleManager::getInstance().setEnabled("AutoSprint", true); }
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_AutoSprintModule_nativeDisable(JNIEnv* env, jobject thiz){ bedrock::modules::ModuleManager::getInstance().setEnabled("AutoSprint", false); }
}
