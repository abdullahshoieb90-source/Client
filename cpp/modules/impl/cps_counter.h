
#pragma once
#include "../module.h"
#include <vector>
#include <chrono>
#include <mutex>

namespace bedrock::modules {

class CPSCounter : public Module {
public:
    CPSCounter() : Module("CPS Counter", "يعرض عدد الضغطات في الثانية", ModuleCategory::RENDER) { enabled = true; }

    void onEnable() override;
    void onDisable() override;

    void onLeftClick();
    void onRightClick();
    int getLeftCPS();
    int getRightCPS();

private:
    std::vector<long long> leftClicks;
    std::vector<long long> rightClicks;
    std::mutex mtx;
    long long nowMs();
};

} // namespace
