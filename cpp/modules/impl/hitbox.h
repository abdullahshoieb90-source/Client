
#pragma once
#include "../module.h"
namespace bedrock::modules { class Hitbox : public Module { public: Hitbox() : Module("Hitbox", "توسيع صندوق الاصطدام", ModuleCategory::COMBAT) {} float size=0.5f; void onEnable() override { enabled=true; } void onDisable() override { enabled=false; } }; }
