cmake_minimum_required(VERSION 3.22.1)
project("bedrock_client")

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Flags
add_compile_options(-O3 -fexceptions -frtti)

# Include dirs
include_directories(
    ${CMAKE_CURRENT_SOURCE_DIR}
    ${CMAKE_CURRENT_SOURCE_DIR}/third_party
    ${CMAKE_CURRENT_SOURCE_DIR}/third_party/imgui
)

# Sources
file(GLOB_RECURSE CLIENT_SOURCES
    "bootstrap/*.cpp"
    "bridge/*.cpp"
    "hooks/*.cpp"
    "modules/*.cpp"
    "loader/*.cpp"
    "runtime/*.cpp"
    "render/*.cpp"
    "imgui/*.cpp"
    "minecraft/*.cpp"
    "utils/*.cpp"
    "memory/*.cpp"
    "jni/*.cpp"
    "logger/*.cpp"
)

add_library(bedrock_client SHARED ${CLIENT_SOURCES})

# Link libraries
find_library(log-lib log)
find_library(android-lib android)
find_library(EGL-lib EGL)
find_library(GLESv3-lib GLESv3)

target_link_libraries(bedrock_client
    ${log-lib}
    ${android-lib}
    ${EGL-lib}
    ${GLESv3-lib}
)

# Third party placeholder (Dobby, MinHook, etc will be added as submodules)
# add_subdirectory(third_party/dobby)
# target_link_libraries(bedrock_client dobby)
