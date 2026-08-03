
#pragma once
#include "../module.h"
namespace bedrock::modules {
class Fullbright : public Module {
public:
    Fullbright() : Module("Fullbright", "زيادة الإضاءة", ModuleCategory::WORLD) {}
    void onEnable() override; void onDisable() override;
};
}
