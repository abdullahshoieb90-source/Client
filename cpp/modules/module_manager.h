
#pragma once
#include "module.h"
#include <vector>
#include <memory>
#include <string>

namespace bedrock::modules {

class ModuleManager {
public:
    static ModuleManager& getInstance();
    void registerModule(std::shared_ptr<Module> mod);
    std::vector<std::shared_ptr<Module>> getAll();
    std::shared_ptr<Module> getModule(const std::string& name);
    void onTick();
    void onRender(float delta);
    void setEnabled(const std::string& name, bool enabled);

private:
    ModuleManager();
    std::vector<std::shared_ptr<Module>> modules;
};

} // namespace
