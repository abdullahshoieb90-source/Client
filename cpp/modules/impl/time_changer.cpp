
#include "time_changer.h"
extern "C" {
#include <jni.h>
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_TimeChangerModule_nativeSetTime(JNIEnv* env, jobject thiz, jlong t){ auto mod = bedrock::modules::ModuleManager::getInstance().getModule("Time Changer"); if(mod) dynamic_cast<bedrock::modules::TimeChanger*>(mod.get())->time=t; }
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_TimeChangerModule_nativeResetTime(JNIEnv* env, jobject thiz){ }
}
