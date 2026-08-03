
#pragma once
#include "../module.h"
#include <chrono>

namespace bedrock::modules {

class FPSCounter : public Module {
public:
    FPSCounter() : Module("FPS Counter", "يعرض عدد الإطارات في الثانية", ModuleCategory::RENDER) { enabled = true; }
    void onEnable() override;
    void onDisable() override;
    void onRender(float delta) override;
    int getFPS() const { return currentFPS; }

private:
    int currentFPS = 0;
    int frameCount = 0;
    std::chrono::steady_clock::time_point lastTime = std::chrono::steady_clock::now();
};

} // namespace
