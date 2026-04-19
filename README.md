# Z4Tax Client

A fire-themed Minecraft Fabric client mod for Minecraft 1.21.4.

## Features

- **ClickGUI** — Draggable, animated mod menu (open with Right Shift)
- **FPS Counter** — Live FPS displayed in the top corner, color-coded
- **Keystrokes** — WASD + LMB/RMB display on screen
- **FPS Boost** — Automatically sets performance options for maximum FPS
- **Config System** — Saves/loads all module states automatically on startup

## Theme

- Dark background (#0d0d0d)
- Orange (#ff6600) & Red (#ff1a1a) fire gradient
- Glow effects on UI elements
- Smooth open/close animations

## Build Instructions

### Prerequisites
- Java 21+ (JDK)
- Internet connection (for Gradle to download dependencies)

### Build
```bash
cd z4tax-client
./gradlew build
```
The compiled `.jar` will be placed at:
```
build/libs/z4tax-client-1.0.0.jar
```

### Install
Copy the `.jar` into your Minecraft `mods/` folder (with Fabric Loader installed).

## Controls

| Key         | Action              |
|-------------|---------------------|
| Right Shift | Open/Close ClickGUI |

## Module Categories

| Category    | Modules                       |
|-------------|-------------------------------|
| HUD         | FPS Counter, Keystrokes       |
| Performance | FPS Boost                     |

## Project Structure

```
z4tax-client/
├── build.gradle
├── gradle.properties
├── settings.gradle
└── src/main/java/com/z4tax/client/
    ├── Z4TaxClient.java          # Main mod entrypoint
    ├── core/
    │   ├── ModuleManager.java    # Manages all modules
    │   └── KeybindManager.java   # Handles keybinds
    ├── events/
    │   └── EventBus.java         # Tick & render event routing
    ├── gui/
    │   └── ClickGUI.java         # Animated ClickGUI
    ├── modules/
    │   ├── Module.java           # Base module class
    │   ├── hud/
    │   │   ├── FpsCounterModule.java
    │   │   └── KeystrokesModule.java
    │   └── performance/
    │       └── FpsBoostModule.java
    ├── config/
    │   └── ConfigManager.java    # JSON config save/load
    ├── mixin/
    │   ├── GameRendererMixin.java
    │   └── ParticleManagerMixin.java
    └── utils/
        ├── RenderUtils.java      # Drawing helpers, rounded rects, glow
        └── ColorTheme.java       # Fire-themed color constants
```
