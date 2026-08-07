# V Client - Minecraft Bedrock Android Launcher

Client متكامل لأندرويد لـ Minecraft Bedrock مع تحسينات وميزات مثل FPS Counter, CPS Counter والمزيد.

## الهيكل العام

```
app/
├── launcher/          # القلب الرئيسي
├── activity/          # شاشات التطبيق
├── fragment/          # أجزاء داخل Activity
├── adapter/           # RecyclerView
├── viewmodel/         # MVVM
├── repository/        # يجمع البيانات
├── database/          # Room/SQLite
├── network/           # Retrofit/OkHttp
├── account/           # إدارة حساب
├── auth/              # OAuth
├── minecraft/
│   ├── package/       # Package Name
│   ├── version/       # إدارة نسخ MC
│   ├── profile/       # ملفات المستخدم
│   ├── instance/      # كل نسخة كـ Instance
│   ├── runtime/       # Runtime الخاص باللعبة
│   ├── manifest/      # قراءة Manifest
│   └── compatibility/ # فحص التوافق
├── environment/
│   ├── sandbox/       # Sandbox بديل لملفات اللعبة
│   ├── filesystem/    # copy/delete/move
│   ├── storage/       # Internal/External/Scoped
│   ├── permission/    # صلاحيات Android
│   └── workspace/     # مجلد عمل مؤقت
├── extractor/         # فك APK/ZIP/JAR
├── installer/         # تثبيت Mods/Packs/Libs
├── updater/           # فحص GitHub Release
├── downloader/        # Resume/MultiThread/Queue
├── loader/
│   ├── dex/           # تحميل .dex/.jar
│   ├── native/        # تحميل .so
│   ├── library/       # تحميل المكتبات
│   └── classloader/   # إدارة ClassLoader
├── runtime/           # بيئة تشغيل التطبيق
├── bootstrap/         # أول كود يعمل
├── bridge/            # Kotlin <-> C++ JNI
├── hook/              # InstallHook/RemoveHook
├── modules/           # Zoom and other client modules
├── plugins/           # نظام Plugins قابل للتوسيع
├── resources/         # موارد داخلية
├── resourcepacks/     # إدارة Resource Packs
├── behaviorpacks/     # إدارة Behavior Packs
├── skins/             # إدارة السكنات
├── worlds/            # إدارة العوالم
├── shaders/           # إدارة الشيدرز
├── cache/             # ملفات مؤقتة
├── backup/            # نسخ احتياطية
├── crash/             # crash.log
├── logger/            # سجلات البرنامج
├── analytics/         # إحصائيات
├── settings/          # إعدادات
├── preference/        # SharedPreferences
├── security/          # Anti Tamper/Root detection
├── service/           # Android Services
├── receiver/          # Broadcast Receivers
├── worker/            # WorkManager
├── notification/      # إشعارات
├── utils/             # دوال مساعدة
└── common/            # أكواد مشتركة

cpp/
├── bootstrap/         # أول كود Native يعمل
├── bridge/            # واجهة JNI ربط Java/Kotlin ب C++
├── hooks/             # Hooks داخل المكتبات الأصلية
├── modules/           # منطق المودات ب C++
├── loader/            # تحميل المكتبات الأصلية
├── runtime/           # دورة حياة Native
├── render/            # الرسم OpenGL/Vulkan
├── imgui/             # واجهة رسومية للقوائم
├── minecraft/         # هياكل اللعبة الأصلية من C++
├── utils/             # دوال مساعدة
├── memory/            # إدارة الذاكرة الآمنة
├── jni/               # JNI_OnLoad, Native Methods
├── logger/            # سجلات Native
└── third_party/       # ImGui, fmt, glm, minhook, dobby

assets/
├── launcher/          # صور واجهة Launcher
├── fonts/             # الخطوط
├── icons/             # الأيقونات
├── config/            # JSON/YAML/INI
├── templates/         # قوالب جاهزة
├── modules/           # إعدادات افتراضية للموديولات
└── resources/         # موارد إضافية
```

## تسلسل التشغيل

```
تشغيل التطبيق
    │
    ▼
Application (ClientApplication.kt)
    │
    ▼
bootstrap/ (Bootstrap.kt + cpp/bootstrap/)
    │
    ▼
launcher/ (Launcher.kt)
    │
    ▼
settings/ + database/
    │
    ▼
account/ + auth/
    │
    ▼
environment/ (sandbox/filesystem/storage/permission/workspace)
    │
    ▼
minecraft/ (package/version/profile/instance/manifest/compatibility)
    │
    ▼
loader/ (dex/native/library/classloader)
    │
    ▼
bridge/ (JNI)  Java -> JNI -> C++
    │
    ▼
cpp/bootstrap/
    │
    ▼
cpp/runtime/
    │
    ▼
cpp/render/ + cpp/modules/ + cpp/hooks/
    │
    ▼
بدء تشغيل اللعبة مع Overlay (FPS/CPS/...)
```

## المميزات المنفذة

- [x] FPS Counter (Kotlin + C++ native)
- [x] CPS Counter (Left/Right clicks per second)
- [x] Zoom module
- [x] Fullbright
- [x] AutoSprint
- [x] Keystrokes, Coordinates HUD
- [x] Module system قابل للتوسعة
- [x] Bridge JNI كامل
- [x] Hook manager (Dobby/MinHook ready)
- [x] ImGui overlay renderer (OpenGL/Vulkan)
- [x] Sandbox environment
- [x] Account system (Offline/Microsoft/Xbox)
- [x] Resource packs / Behavior packs / Skins / Worlds / Shaders managers
- [x] Backup, Crash handler, Logger, Analytics
- [x] Security (Root detection, Signature check)

## البناء

```bash
./gradlew assembleDebug
```

يتطلب:
- Android SDK 34
- NDK 26+
- CMake 3.22.1

## ملاحظة عن Minecraft Bedrock

يتعرّف V Client تلقائيًا على الإصدار المثبّت من حزمة Minecraft الرسمية
(`com.mojang.minecraftpe`) ثم يجهّز Instance خاصة داخل التطبيق في:

- `files/instances/{instance}` لإدارة `options.txt`, worlds, resource packs, behavior packs, skins
- `files/sandbox/{instance}/games/com.mojang` لبناء بيئة Bedrock-style خاصة بالتطبيق
- `files/instances/{instance}/exports/active/games/com.mojang` كنسخة جاهزة للتصدير

بعد ذلك يحاول V Client — عندما يسمح Android ومسار التخزين المشترك متاحًا — مزامنة
الـ Instance النشطة إلى مسار Minecraft المشترك التقليدي `games/com.mojang` ثم يفتح
Activity الخاصة باللعبة. إذا لم يسمح النظام بالكتابة إلى هذا المسار، تبقى البيئة
الخاصّة جاهزة محليًا داخل V Client ويستمر التشغيل العادي للتطبيق الرسمي.

لا يحاول مسار التشغيل العادي تحميل `libminecraftpe.so` من حزمة أخرى، لأن عزل
التطبيقات والـ linker في إصدارات Android الحديثة يمنع ذلك. يبقى
`libbedrock_client.so` مكوّنًا اختياريًا للتطوير ولا يمنع فتح اللعبة إذا تعذّر تحميله.

الهدف تعليمي ولبناء Launcher محسن.

