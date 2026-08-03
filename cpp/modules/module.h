
#pragma once
#include <string>

namespace bedrock::modules {

enum class ModuleCategory { RENDER, COMBAT, MOVEMENT, WORLD, CLIENT };

class Module {
public:
    Module(const std::string& name, const std::string& desc, ModuleCategory cat)
        : name(name), description(desc), category(cat) {}
    virtual ~Module() = default;

    virtual void onEnable() = 0;
    virtual void onDisable() = 0;
    virtual void onTick() {}
    virtual void onRender(float delta) {}
    virtual void onUpdate() {}

    std::string name;
    std::string description;
    ModuleCategory category;
    bool enabled = false;
};

} // namespace
