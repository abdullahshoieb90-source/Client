# Architecture - تسلسل التشغيل

## 1. Application Layer
- `ClientApplication` (Application)
  - init CrashHandler
  - init Logger
  - SecurityManager checks
  - SettingsManager load
  - WorkspaceManager prepare
  - Bootstrap initialize

## 2. Bootstrap
- Tries to load the optional `libbedrock_client.so` via NativeLoader
- Initializes BridgeManager when JNI is available
- Always initializes LauncherManager, even when the optional native component fails

## 3. Launcher
- Reads the installed package metadata from `com.mojang.minecraftpe`
- Uses its real `versionName` instead of a hard-coded version
- Prepares a private instance under `/files/instances/{instance}`
- Builds a Bedrock-style sandbox under `/files/sandbox/{instance}/games/com.mojang`
- Best-effort mirrors the active instance to legacy shared Minecraft storage when Android allows it
- Opens Minecraft through its Android launcher Activity

## 4. Environment
- instances: `/files/instances/{instance}` with profile, worlds, resource packs, behavior packs, skins, shaders, exports
- sandbox: `/files/sandbox/{instance}/games/com.mojang/...`
- export bundle: `/files/instances/{instance}/exports/active/games/com.mojang`
- filesystem: copy/delete/move
- storage: internal/external/scoped
- permission: Android permissions
- workspace: workspace/cache/tmp

## 5. Minecraft
- package: check com.mojang.minecraftpe installed
- version: detected dynamically from the installed package
- profile: Player, Options
- instance: Instance #1, #2
- runtime: start native runtime
- manifest: parse AndroidManifest, Manifest.json
- compatibility: check ARM64, SDK

## 6. Loader
- dex: DexClassLoader for .dex/.jar
- native: System.loadLibrary .so
- library: load .so from directory
- classloader: CustomClassLoader

## 7. Bridge JNI
- Java/Kotlin <-> C++
- BridgeManager native methods
- nativeInit, nativeAttachActivity, nativeLaunchGame

## 8. Native C++
- cpp/bootstrap: first native code
- cpp/bridge: JNI bindings
- cpp/runtime: lifecycle
- cpp/hooks: install hooks (eglSwapBuffers, input, tick)
- cpp/render: OpenGL/Vulkan overlay
- cpp/imgui: ImGui menu
- cpp/modules: FPS, CPS, Zoom, etc.
- cpp/minecraft: access entities/world
- cpp/memory: safe read/write, pattern scan, module base

## 9. Modules
All modules categorized:
- RENDER: FPS Counter, CPS Counter, Zoom, Keystrokes, Coordinates
- MOVEMENT: AutoSprint
- WORLD: Fullbright, TimeChanger
- CLIENT: others

Each module: onEnable/onDisable/onTick/onRender

## 10. UI
- MainActivity with BottomNavigation
- HomeFragment with Launch button
- ModsFragment with ModuleAdapter
- OverlayService for HUD

## Data Flow

UI -> ViewModel -> Repository -> Network/DB/Files -> Bridge -> Native -> Minecraft -> Render back to UI overlay
