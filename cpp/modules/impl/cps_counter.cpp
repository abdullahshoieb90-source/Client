
#include "cps_counter.h"
#include "../module_manager.h"
#include <jni.h>
#include "../../logger/logger.h"
#include "../../render/renderer.h"
#include <algorithm>

namespace bedrock::modules {

long long CPSCounter::nowMs() {
    return std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now().time_since_epoch()).count();
}

void CPSCounter::onEnable() { enabled = true; LOGI("CPSCounter", "Enabled"); }
void CPSCounter::onDisable() { enabled = false; LOGI("CPSCounter", "Disabled"); }

void CPSCounter::onLeftClick() {
    std::lock_guard<std::mutex> lock(mtx);
    leftClicks.push_back(nowMs());
}

void CPSCounter::onRightClick() {
    std::lock_guard<std::mutex> lock(mtx);
    rightClicks.push_back(nowMs());
}

int CPSCounter::getLeftCPS() {
    std::lock_guard<std::mutex> lock(mtx);
    long long now = nowMs();
    leftClicks.erase(std::remove_if(leftClicks.begin(), leftClicks.end(), [now](long long t){ return now - t > 1000; }), leftClicks.end());
    return leftClicks.size();
}

int CPSCounter::getRightCPS() {
    std::lock_guard<std::mutex> lock(mtx);
    long long now = nowMs();
    rightClicks.erase(std::remove_if(rightClicks.begin(), rightClicks.end(), [now](long long t){ return now - t > 1000; }), rightClicks.end());
    return rightClicks.size();
}

} // namespace

extern "C" {
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_CPSCounterModule_nativeSetEnabled(JNIEnv* env, jobject thiz, jboolean e) {
    bedrock::modules::ModuleManager::getInstance().setEnabled("CPS Counter", e);
}
}
