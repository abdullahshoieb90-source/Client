
#include "memory.h"
#include <sys/mman.h>
#include <unistd.h>
#include <cstdio>
#include <cstring>
#include <cinttypes>
#include <dlfcn.h>

namespace bedrock::memory {

bool readMemory(void* address, void* buffer, size_t size) {
    if (!address || !buffer) return false;
    std::memcpy(buffer, address, size);
    return true;
}

bool writeMemory(void* address, void* buffer, size_t size) {
    if (!address || !buffer) return false;
    // Make writable
    long pageSize = sysconf(_SC_PAGESIZE);
    uintptr_t pageStart = (uintptr_t)address & ~(pageSize - 1);
    mprotect((void*)pageStart, pageSize, PROT_READ | PROT_WRITE | PROT_EXEC);
    std::memcpy(address, buffer, size);
    mprotect((void*)pageStart, pageSize, PROT_READ | PROT_EXEC);
    return true;
}

void* findPattern(const char* pattern, const char* mask, void* start, size_t size) {
    // Simple pattern scan
    for (size_t i = 0; i < size; ++i) {
        bool found = true;
        for (size_t j = 0; mask[j]; ++j) {
            if (mask[j] == 'x' && pattern[j] != ((char*)start)[i+j]) { found = false; break; }
        }
        if (found) return (char*)start + i;
    }
    return nullptr;
}

uintptr_t getModuleBase(const char* moduleName) {
    // Parse /proc/self/maps
    FILE* f = fopen("/proc/self/maps", "r");
    if (!f) return 0;
    char line[512];
    uintptr_t base = 0;
    while (fgets(line, sizeof(line), f)) {
        if (strstr(line, moduleName)) {
            sscanf(line, "%" PRIxPTR, &base);
            break;
        }
    }
    fclose(f);
    return base;
}

bool protectMemory(void* address, size_t size, int protection) {
    return mprotect(address, size, protection) == 0;
}

} // namespace
