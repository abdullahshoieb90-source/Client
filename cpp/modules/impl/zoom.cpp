
#include "zoom.h"
#include "../../logger/logger.h"

namespace bedrock::modules {
void Zoom::onEnable() {
    LOGI("Zoom", "Enabled level %f", level);
    enabled = true;
    // Hook FOV: patch Minecraft field-of-view calculation
}
void Zoom::onDisable() { enabled = false; LOGI("Zoom", "Disabled"); }
}

extern "C" {
#include <jni.h>
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_ZoomModule_nativeEnable(JNIEnv* env, jobject thiz, jfloat lvl) {
    auto mod = bedrock::modules::ModuleManager::getInstance().getModule("Zoom");
    if (mod) { dynamic_cast<bedrock::modules::Zoom*>(mod.get())->setLevel(lvl); mod->enabled = true; mod->onEnable(); }
}
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_ZoomModule_nativeDisable(JNIEnv* env, jobject thiz) {
    bedrock::modules::ModuleManager::getInstance().setEnabled("Zoom", false);
}
}
