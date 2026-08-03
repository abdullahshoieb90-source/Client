
#pragma once
#include "../module.h"
namespace bedrock::modules { class TimeChanger : public Module { public: TimeChanger() : Module("Time Changer","تغيير وقت العالم",ModuleCategory::WORLD){} long time=6000; void onEnable() override { enabled=true; } void onDisable() override { enabled=false; } }; }
