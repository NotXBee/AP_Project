# Angry Birds Clone 🎯🐦

A LibGDX‑powered, cross‑platform 2D physics game inspired by the classic “Angry Birds,” developed as part of IIIT‑Delhi’s Advanced Programming coursework. Players sling birds at block‑and‑pig structures across three increasing‑difficulty levels, unlocking new mechanics and saving/loading progress between sessions.

---

## 📋 Project Overview

- **Engine & Framework**  
  - Built on **LibGDX** (with LWJGL3 backend) for desktop and web deployment  
  - Leverages Box2D–style physics for realistic projectile arcs, collisions, and object stacking  

- **Core Gameplay**  
  - **Three Bird Types**  
    1. **Red Bird** – Standard launch  
    2. **Matilda** – Can be re‑launched once mid‑flight  
    3. **Terrence** – Heavy‑impact bird with larger collision radius  
  - **Three Block Materials**  
    - **Glass** (low HP), **Wood** (medium HP), **Metal** (high HP)  
  - **Three Pig Variants**  
    - **Normal Pig** (low HP, small radius)  
    - **Helmet Pig** (high HP, larger radius)  
    - **Crowned Pig** (medium HP, medium radius)  
  - **Level Progression**: Clear all pigs to advance; three levels of escalating challenge  

- **Persistence & UX**  
  - **Save & Load** your session via `test.ser`–backed serialization  
  - Pause menu with **Save & Exit**, **Resume**, and **Main Menu** options  
  - Simple, intuitive mouse/touch controls and on‑screen UI feedback  

---

## 🗂️ Repository Structure

```
AP_Project/
├── core/               # game logic, screens, and physics setup
│   ├── src/com/ap/game/
│   │   ├── GameMain.java
│   │   ├── ScreenManager.java
│   │   └── entities/  
│   └── assets/         # textures, sounds, spritesheets
├── lwjgl3/             # desktop launcher & Gradle configs
│   └── src/com/ap/launcher/
├── assets/             # shared asset definitions
├── UML.png             # high‑level class and package diagram
├── build.gradle        # root Gradle build script
├── gradlew, gradlew.bat
├── settings.gradle
└── test.ser            # example serialized save file
```

---

## ⚙️ Tech Stack & Dependencies

* **Java 11+**
* **LibGDX** (core + LWJGL3 backend)
* **Gradle** (wrapper included)
* No external libraries beyond LibGDX’s standard modules

---

## 🚀 Getting Started

1. **Clone the repo**

   ```bash
   git clone https://github.com/NotXBee/AP_Project.git
   cd AP_Project
   ```
2. **Build & Run (Desktop)**

   ```bash
   ./gradlew lwjgl3:run
   ```
3. **Play**

   * **Drag & release** birds with mouse or touch
   * **Pause** with ESC to save/load/exit

---

## 📈 UML & Design

* **UML.png** in the root illustrates the high‑level class relationships:

  * `GameMain` → initializes `ScreenManager`
  * `GameScreen` ↔ Physics world ↔ Entity classes (Bird, Pig, Block)
  * Serialization handled by `SaveManager`

---

## 🎥 Demo

[► Watch a quick gameplay demo](https://youtu.be/your-demo-link)

---

## 👥 Contributors

* **Aditya Nuli** (2023360)
* **Piyush Keshan** (2023376)

---

## 📜 License

Distributed under the **MIT License**. See [LICENSE](LICENSE) for details.
Feel free to fork, adapt, and extend—just please leave a credit back to the original authors!
