
#include "mc.h"
#include "../logger/logger.h"

namespace bedrock::mc {

Minecraft& Minecraft::getInstance() {
    static Minecraft instance;
    return instance;
}

bool Minecraft::isInGame() {
    // Check if Level* != nullptr via memory scanning
    return false;
}

std::string Minecraft::getPlayerName() { return "Player"; }

void Minecraft::getPlayerPos(float& x, float& y, float& z) {
    x = y = z = 0.0f;
}

} // namespace
