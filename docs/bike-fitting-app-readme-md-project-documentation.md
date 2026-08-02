# 🚴‍♂️ **Bike Fitting App**

> **Mobile application (Android) for interactive bike fitting** - **comprehensive tool** for optimizing gravel/road bike position.
> **Optimize your gravel/road bike position** in 7 steps, with manual measurements, AR, and PDF export.

---

## 📌 **Table of Contents**
- [📌 About the Project](#-about-the-project)
- [🎯 Features](#-features)
- [📱 System Requirements](#-system-requirements)
- [🛠️ Technologies](#-technologies)
- [🚀 Getting Started](#-getting-started)
- [📂 Project Structure](#-project-structure)
- [🏗️ Architecture](#-architecture)
- [📊 Reference Data](#-reference-data)
- [🧪 Testing](#-testing)
- [📦 Building and Deployment](#-building-and-deployment)
- [🤖 Collaboration with AI Agent (Vibe Coding)](#-collaboration-with-ai-agent-vibe-coding)
- [📜 Additional Documentation](#-additional-documentation)
- [🙏 Contribution and License](#-contribution-and-license)

---

## 📌 **About the Project**

### **🎯 Purpose**
The *Bike Fitting App* was created as a **comprehensive tool** for optimizing bike position. 
Its main goal is to **improve comfort, performance, and ergonomics** during cycling through **precise position adjustment** based on a **7-step bike fitting process**. 

### **💡 Why This Application?**
- **Education:** Learn **proper bike fitting techniques** step by step.
- **Practicality:** Support **manual measurements** or **automatic measurements (AR)**.
- **Documentation:** Generate **PDF reports** with results and recommendations.
- **Offline:** **100% functionality without internet** - ideal for cyclists in the field.

### **📖 Knowledge Source**
The application is based on **industry-standard bike fitting practices** for gravel and road bikes, including:
- **7 bike fitting steps** (saddle height, tilt, distance, etc.).
- **Reference tables** (height → saddle height, height difference, etc.).
- **Expert tips** from the cycling industry.

---

## 🎯 **Features**

### **✅ MVP (Version 1.0.0)**
| **Module** | **Description** | **Status** |
|-----------|----------------|------------|
| **Step-by-step guide** | 7 bike fitting steps with instructions, illustrations, and tips. | ✅ |
| **Manual measurements** | Enter data (height, leg length, shoulder width, etc.). | ✅ |
| **Parameter calculator** | Calculate optimal values (saddle height, distance, etc.) based on tables. | ✅ |
| **Results and visualization** | Summary of results + bike diagram with measurements. | ✅ |
| **PDF export** | Generate PDF report with measurements and recommendations. | ✅ |
| **Measurement history** | Store and browse previous sessions. | ✅ |
| **Settings** | Language (PL/EN), dark mode, units (metric/imperial). | ✅ |
| **AR measurements** | Bike/body scanning using ARCore (optional). | ⚠️ Post-MVP |

### **🔜 Post-MVP (Planned)**
| **Module** | **Description** | **Priority** |
|-----------|----------------|--------------|
| **Strava Integration** | Import biometric data, export results as notes. | 🥇 High |
| **Garmin Integration** | Sync with Garmin devices (cadence, power, etc.). | 🥈 Medium |
| **Cloud Sync** | Backup measurement history (Firebase/Google Drive). | 🥉 Low |
| **Result Sharing** | Share reports with others (e.g., mechanics). | 🥉 Low |

---

## 📱 **System Requirements**

| **Parameter** | **Requirement** | **Notes** |
|--------------|----------------|-----------|
| **Operating System** | Android 17 (API 34) and above | Required for ARCore (optional). |
| **RAM** | 2 GB+ | 4 GB recommended. |
| **Storage Space** | 100 MB+ | APK + local data. |
| **ARCore** | Optional | Required for AR measurements. |
| **Camera** | Yes | Required for AR measurements. |

---

## 🛠️ **Technologies**

| **Category** | **Technology** | **Version** | **Purpose** |
|--------------|----------------|------------|---------|
| **Language** | Kotlin | 1.9.0 | Primary application language. |
| **UI Framework** | Jetpack Compose | 1.5.0 | User interface development. |
| **Architecture** | MVVM + Clean Architecture | - | Separation of presentation layer from logic. |
| **Dependency Injection** | Hilt | 2.48 | Dependency management. |
| **Database** | Room | 2.6.0 | Local storage of measurement history. |
| **AR** | ARCore | 1.40.0 | 3D scanning (optional). |
| **Camera** | CameraX | 1.3.0 | Device camera handling. |
| **PDF** | iTextPDF | 7.2.5 | PDF report generation. |
| **Navigation** | Compose Navigation | 2.7.5 | Navigation between screens. |

---

## 🚀 **Getting Started**

### **📥 Downloading and Installation**

#### **1. Clone the Repository**
```bash
# Clone the repository
 git clone https://github.com/0x-void/bike-fitting-app.git

# Navigate to project directory
cd bike-fitting-app
```

#### **2. Open in Android Studio**
1. Launch **Android Studio** (version Giraffe 2022.3.1+).
2. Select **File → Open** and choose the project folder.
3. Wait for **Gradle sync** (may take 1-2 minutes).

#### **3. Environment Configuration**
- **JDK:** Ensure you have **JDK 17** installed.
- **Android SDK:** Install **Android SDK 34** (Android 17).
- **ARCore:** If you want to test AR measurements, install **Google Play Services** (in Android Studio: **Tools → SDK Manager → Google Play services**).

---

## 📂 **Project Structure**

```
bike-fitting-app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/optibike/fitting/
│   │   │   │   ├── di/                  # Hilt Modules
│   │   │   │   │   ├── AppModule.kt
│   │   │   │   │   └── RepositoryModule.kt
│   │   │   │   ├── data/               # Data Layer
│   │   │   │   │   ├── local/           # Room Database
│   │   │   │   │   │   ├── dao/         # Data Access Objects
│   │   │   │   │   │   │   └── MeasurementDao.kt
│   │   │   │   │   │   ├── entities/     # Room Entities
│   │   │   │   │   │   │   └── MeasurementEntity.kt
│   │   │   │   │   │   └── BikeFittingDatabase.kt
│   │   │   │   │   └── repository/     # Repositories
│   │   │   │   │       └── MeasurementRepository.kt
│   │   │   │   ├── domain/             # Domain Layer
│   │   │   │   │   ├── model/          # Business Models
│   │   │   │   │   │   └── Measurement.kt
│   │   │   │   │   ├── usecase/        # Use Cases
│   │   │   │   │   │   ├── CalculateSaddleHeight.kt
│   │   │   │   │   │   ├── CalculateHandlebarHeight.kt
│   │   │   │   │   │   └── ...
│   │   │   │   │   └── utils/          # Helpers
│   │   │   │   │       ├── BikeFittingFormulas.kt
│   │   │   │   │       └── Constants.kt
│   │   │   │   ├── presentation/      # Presentation Layer
│   │   │   │   │   ├── viewmodel/     # ViewModels
│   │   │   │   │   │   ├── GuideViewModel.kt
│   │   │   │   │   │   └── MeasurementViewModel.kt
│   │   │   │   │   ├── screens/        # Compose Screens
│   │   │   │   │   │   ├── guide/
│   │   │   │   │   │   │   ├── GuideScreen.kt
│   │   │   │   │   │   │   └── StepDetailScreen.kt
│   │   │   │   │   │   ├── measurement/
│   │   │   │   │   │   │   ├── ManualMeasurementScreen.kt
│   │   │   │   │   │   │   └── ARMeasurementScreen.kt
│   │   │   │   │   │   ├── calculator/
│   │   │   │   │   │   │   └── CalculatorScreen.kt
│   │   │   │   │   │   ├── results/
│   │   │   │   │   │   │   └── ResultsScreen.kt
│   │   │   │   │   │   ├── history/
│   │   │   │   │   │   │   └── HistoryScreen.kt
│   │   │   │   │   │   └── settings/
│   │   │   │   │   │       └── SettingsScreen.kt
│   │   │   │   │   ├── components/    # Shared Components
│   │   │   │   │   │   ├── BikeFittingCard.kt
│   │   │   │   │   │   ├── NeonButton.kt
│   │   │   │   │   │   └── ...
│   │   │   │   │   ├── theme/         # Theme (Cyberpunk)
│   │   │   │   │   │   ├── Color.kt
│   │   │   │   │   │   ├── Theme.kt
│   │   │   │   │   │   └── Type.kt
│   │   │   │   │   └── navigation/    # Navigation
│   │   │   │   │       ├── NavGraph.kt
│   │   │   │   │       └── AppNavHost.kt
│   │   │   │   └── MainActivity.kt     # Entry Point
│   │   │   └── res/                   # Resources
│   │   │       ├── drawable/          # Graphics
│   │   │       ├── values/            # Strings, colors, styles
│   │   │       │   ├── colors.xml
│   │   │       │   ├── strings.xml
│   │   │       │   ├── strings-pl.xml  # Polish Translations
│   │   │       │   └── themes.xml
│   │   │       └── values-pl/         # Polish Translations
│   │   │           └── strings.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts              # Module Configuration
├── build.gradle.kts                  # Project Configuration
├── settings.gradle.kts              # Project Settings
├── gradle.properties                 # Gradle Properties
└── README.md                          # This Document
```

---

## 🏗️ **Architecture**

### **📌 Clean Architecture + MVVM**
The application is based on **Clean Architecture** with **MVVM** (Model-View-ViewModel):

```mermaid
flowchart TD
    subgraph Presentation["Presentation Layer (UI)"]
        A[Screens (Compose)] --> B[ViewModels]
    end
    
    subgraph Domain["Domain Layer (Business Logic)"]
        B --> C[Use Cases]
        C --> D[Models]
    end
    
    subgraph Data["Data Layer"]
        C --> E[Repositories]
        E --> F[Local Data (Room)]
        E --> G[Remote Data (API)]
    end
    
    classDef presentation fill:#00FFFF,stroke:#00FFFF,color:#0A0A0F
    classDef domain fill:#FF00FF,stroke:#FF00FF,color:#0A0A0F
    classDef data fill:#00FF88,stroke:#00FF88,color:#0A0A0F
    
    class A,B presentation
    class C,D domain
    class E,F,G data
```

### **🔌 Dependency Injection (Hilt)**
- **`@HiltAndroidApp`** - Application.
- **`@Module` + `@InstallIn`** - Hilt modules.
- **`@Inject`** - Dependency injection.

---

## 📊 **Reference Data**

### **📖 Standard Bike Fitting Tables**

#### **Table 1: Saddle Height (Road vs. Gravel)**
| **Height (cm)** | **Road (mm)** | **Gravel (mm)** |
|-----------------|---------------|-----------------|
| 150             | 600           | 598             |
| 160             | 660           | 657             |
| 170             | 720           | 717             |
| 180             | 780           | 777             |
| 190             | 840           | 837             |
| 200             | 900           | 897             |

**Formula:** `Saddle Height (mm) = Height (cm) * 0.45` (for Road).
**Gravel:** `Saddle Height (Road) - 2-3 mm` (for better control).

#### **Table 2: Saddle-Handlebar Height Difference (C = A-B)**
| **Height (cm)** | **Road (mm)** | **Gravel (mm)** |
|-----------------|---------------|-----------------|
| 150             | 50            | 20              |
| 160             | 60            | 30              |
| 170             | 70            | 40              |
| 180             | 81            | 51              |
| 190             | 96            | 66              |
| 200             | 111           | 81              |

---

## 🧪 **Testing**

### **🔹 Unit Tests (JUnit 5)**
- **Location:** `src/test/java/...`
- **Example:**
  ```kotlin
  @Test
  fun `calculateSaddleHeight for ROAD returns correct value`() {
      val result = calculateSaddleHeight(180, BikeType.ROAD)
      assertEquals(810, result) // 180 * 0.45 = 81
  }
  ```

### **🔹 UI Tests (Espresso + Compose)**
- **Location:** `src/androidTest/java/...`
- **Example:**
  ```kotlin
  @Test
  fun guideScreen_displaysAllSteps() {
      composeTestRule.onAllNodesWithText("Step 1: Saddle Height").assertExists()
  }
  ```

### **🔹 Manual Tests**
- **Devices:** Pixel 7 Pro (Android 17), Samsung Galaxy S23.
- **Scenarios:**
  1. Go through **all 7 steps** of the guide.
  2. **Manual measurements** + **PDF export**.
  3. **AR measurements** (if available).
  4. **Change settings** (language, dark mode).

---

## 📦 **Building and Deployment**

### **🔨 Building APK**
```bash
# Debug APK
./gradlew assembleDebug

# Release APK
./gradlew assembleRelease
```

**APK file** will appear in:
```
app/build/outputs/apk/debug/app-debug.apk
app/build/outputs/apk/release/app-release.apk
```

### **🚀 Deploying on Device**
1. **Transfer APK** to device (e.g., via **ADB**):
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```
2. **Run:**
   ```bash
   adb shell am start -n com.optibike.fitting/.MainActivity
   ```

### **📲 Deploying to Google Play**
1. **Generate signed APK:**
   ```bash
   ./gradlew bundleRelease
   ```
2. **AAB file** will appear in:
   ```
   app/build/outputs/bundle/release/app-release.aab
   ```
3. **Upload to Google Play Console** and publish.

---

## 🤖 **Collaboration with AI Agent (Vibe Coding)**

### **🎯 How to Use the AI Agent?**
1. **Provide context:**
   - **Documents:** Link **canvases** (e.g., *Design Guidelines*, *Requirements*).
   - **Code:** Show **code snippets** (e.g., `MeasurementEntity.kt`).
   - **Errors:** Paste **stack trace** (e.g., `Caused by: java.lang.NullPointerException`).

2. **Ask specific questions:**
   - **Good:** *"Generate the `CalculateSaddleHeight` class with formulas from Table 1."*
   - **Bad:** *"Write an app."* (too general).

3. **Expect specific answers:**
   - **Kotlin code** (with KDoc).
   - **Mermaid diagrams** (architecture, flow).
   - **JUnit/Espresso tests**.

### **📌 Example Queries for AI Agent**
| **Goal** | **Query** | **Expected Response** |
|---------|--------------|--------------------------|
| Code generation | *"Generate `GuideViewModel.kt` with a list of 7 steps."* | ViewModel code with data. |
| Debugging | *"Why doesn't `RoomDatabase` create an instance? Error: `...`"* | Error analysis + fix. |
| UI design | *"Create `StepDetailScreen.kt` in Compose."* | Screen code with UI. |
| Testing | *"Write JUnit tests for `BikeFittingFormulas.kt`."* | Unit tests. |

### **🔗 Documentation for AI Agent**
All **canvases** are available in the project:
- [Design Guidelines](bike-fitting-design-guidelines) - UI/UX, styles, components.
- [Functional Requirements](bike-fitting-requirements) - 7 steps, measurements, calculator.
- [Implementation Plan](bike-fitting-implementation-plan) - Schedule, milestones, risks, sprints.
- [Vibe Coding Methodology](bike-fitting-methodology) - Workflow, AI roles.
- [Tools and Environment](bike-fitting-tools-environment) - Android Studio, dependencies.
- [Post-MVP](bike-fitting-post-mvp) - Strava/Garmin integrations.
- [UI Mockups](bike-fitting-ui-mockups) - Interactive screen prototypes.
- [User Flow](bike-fitting-user-flow) - User paths, scenarios.

---

## 📜 **Additional Documentation**

| **Document** | **Description** | **Link** |
|-------------|----------------|----------|
| Design Guidelines | UI/UX, Cyberpunk style, navigation, components. | [Open](bike-fitting-design-guidelines) |
| Functional Requirements | 7 steps, measurements, calculator, PDF, reference tables. | [Open](bike-fitting-requirements) |
| Implementation Plan | Schedule (Gantt), milestones, risks, sprints. | [Open](bike-fitting-implementation-plan) |
| Vibe Coding Methodology | AI workflow, roles, testing, debugging. | [Open](bike-fitting-methodology) |
| Tools and Environment | Android Studio, dependencies, structure, conventions. | [Open](bike-fitting-tools-environment) |
| Post-MVP | Strava/Garmin integrations, roadmap. | [Open](bike-fitting-post-mvp) |
| UI Mockups | Interactive screen prototypes (HTML). | [Open](bike-fitting-ui-mockups) |
| User Flow | User paths, scenarios, Mermaid diagrams. | [Open](bike-fitting-user-flow) |

---

## 🙏 **Contribution and License**

### **🤝 Contribution**
- **0x-void Dev Team** - Lead developer.
- **AI Agent (Mistral/Le Chat)** - Co-author (documentation, code, tests).

### **📜 License**
The project is licensed under **MIT** - free use, modification, and distribution.

```
MIT License

Copyright (c) 2026 0x-void Dev Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 📞 **Contact**

| **Type** | **Information** |
|---------|----------------|
| **Email** | dev@0x-void.tech |
| **GitHub** | [0x-void/bike-fitting-app](https://github.com/0x-void/bike-fitting-app) |
| **Documentation** | [Bike Fitting Canvases](#-additional-documentation) |

---

## 🎉 **Summary**

The *Bike Fitting App* is a **complete tool** for **optimizing gravel/road bike position**, based on **industry-standard practices**. 

**🚀 Ready to use?**
1. **Clone the repository**.
2. **Open in Android Studio**.
3. **Run on device**.
4. **Enjoy better riding comfort!**

---

**🔹 Last Updated:** `2026-08-01`
**🔹 Version:** `1.0.0`

**Stay safe on the Net, Netrunner.** 🚴‍♂️💻