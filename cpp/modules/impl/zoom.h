
#pragma once
#include "../module.h"
namespace bedrock::modules {
class Zoom : public Module {
public:
    Zoom() : Module("Zoom", "تكبير الرؤية", ModuleCategory::RENDER) {}
    void onEnable() override;
    void onDisable() override;
    void setLevel(float lvl) { level = lvl; }
private:
    float level = 3.0f;
};
}
