
#include "fps_counter.h"
#include "../../logger/logger.h"
#include "../../render/renderer.h"

namespace bedrock::modules {

void FPSCounter::onEnable() {
    LOGI("FPSCounter", "Enabled");
    enabled = true;
}

void FPSCounter::onDisable() {
    LOGI("FPSCounter", "Disabled");
    enabled = false;
}

void FPSCounter::onRender(float delta) {
    frameCount++;
    auto now = std::chrono::steady_clock::now();
    auto elapsed = std::chrono::duration_cast<std::chrono::milliseconds>(now - lastTime).count() / 1000.0f;
    if (elapsed >= 1.0f) {
        currentFPS = static_cast<int>(frameCount / elapsed);
        frameCount = 0;
        lastTime = now;

        // Send to renderer overlay
        render::Renderer::getInstance().setOverlayText("FPS", std::to_string(currentFPS));
    }
}

} // namespace

extern "C" {
JNIEXPORT void JNICALL Java_com_bedrock_client_modules_impl_FPSCounterModule_nativeSetEnabled(JNIEnv* env, jobject thiz, jboolean e) {
    bedrock::modules::ModuleManager::getInstance().setEnabled("FPS Counter", e);
}
}
