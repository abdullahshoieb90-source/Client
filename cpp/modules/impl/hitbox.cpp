
#include "hitbox.h"
#include "../module_manager.h"
#include <jni.h>
#include "../../logger/logger.h"
extern "C" {
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_HitboxModule_nativeEnable(JNIEnv* env, jobject thiz, jfloat s){
    auto mod = bedrock::modules::ModuleManager::getInstance().getModule("Hitbox");
    if(mod){ dynamic_cast<bedrock::modules::Hitbox*>(mod.get())->size = s; mod->enabled=true; mod->onEnable(); }
}
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_HitboxModule_nativeDisable(JNIEnv* env, jobject thiz){
    bedrock::modules::ModuleManager::getInstance().setEnabled("Hitbox", false);
}
}
