
#include "hitbox.h"
#include "../../logger/logger.h"
namespace bedrock::modules {
void Hitbox::onEnable() { enabled=true; LOGI("Hitbox","Enabled size %f", size); }
void Hitbox::onDisable() { enabled=false; }
}
extern "C" {
#include <jni.h>
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_HitboxModule_nativeEnable(JNIEnv* env, jobject thiz, jfloat s){
    auto mod = bedrock::modules::ModuleManager::getInstance().getModule("Hitbox");
    if(mod){ dynamic_cast<bedrock::modules::Hitbox*>(mod.get())->size = s; mod->enabled=true; mod->onEnable(); }
}
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_HitboxModule_nativeDisable(JNIEnv* env, jobject thiz){
    bedrock::modules::ModuleManager::getInstance().setEnabled("Hitbox", false);
}
}
