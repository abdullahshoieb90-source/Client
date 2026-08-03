
#pragma once
#include "../module.h"
namespace bedrock::modules { class ESP : public Module { public: ESP() : Module("ESP", "رؤية الكيانات", ModuleCategory::RENDER) {} void onEnable() override { enabled = true; } void onDisable() override { enabled = false; } }; }
