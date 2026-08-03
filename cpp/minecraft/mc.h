
#pragma once
#include <string>

// التعامل مع هياكل وواجهات اللعبة الأصلية من جهة C++
// مثل الوصول إلى الكيانات أو بيانات العالم إذا كانت متاحة عبر الواجهات المستخدمة.

namespace bedrock::mc {

class Minecraft {
public:
    static Minecraft& getInstance();
    bool isInGame();
    std::string getPlayerName();
    void getPlayerPos(float& x, float& y, float& z);

private:
    Minecraft() = default;
};

class Entity {
public:
    float x, y, z;
    std::string name;
};

} // namespace
