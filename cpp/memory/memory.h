
#pragma once
#include <cstdint>
#include <cstddef>

namespace bedrock::memory {

// إدارة الذاكرة، مثل القراءة والكتابة الآمنة داخل الذاكرة الخاصة بالعملية التي يعمل فيها الكود.

bool readMemory(void* address, void* buffer, size_t size);
bool writeMemory(void* address, void* buffer, size_t size);
void* findPattern(const char* pattern, const char* mask, void* start, size_t size);
uintptr_t getModuleBase(const char* moduleName);
bool protectMemory(void* address, size_t size, int protection);

} // namespace
