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
- Loads native library `libbedrock_client.so` via NativeLoader
- BridgeManager init (JNI)
- Calls LauncherManager initialize

## 3. Launcher
- SandboxManager.createSandbox(instanceId)
- VersionManager compatibility check
- InstanceManager get instance
- Bootstrap.launchMinecraft -> native

## 4. Environment
- sandbox: /files/sandbox/{instance}/games, worlds
- filesystem: copy/delete/move
- storage: internal/external/scoped
- permission: Android permissions
- workspace: workspace/cache/tmp

## 5. Minecraft
- package: check com.mojang.minecraftpe installed
- version: 1.21.100, 1.21.130
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
- RENDER: FPS Counter, CPS Counter, Zoom, ESP, Keystrokes, Coordinates
- COMBAT: Hitbox
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
