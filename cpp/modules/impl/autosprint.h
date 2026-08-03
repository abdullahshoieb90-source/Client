
#pragma once
#include "../module.h"
namespace bedrock::modules { class AutoSprint : public Module { public: AutoSprint() : Module("AutoSprint","ركض تلقائي",ModuleCategory::MOVEMENT){} void onEnable() override; void onDisable() override; }; }
