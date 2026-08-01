# 🛠️ **BIKE FITTING APP: TOOLS, ENVIRONMENT AND CONVENTIONS**
**Version:** `1.0.0`
**Date:** `2026-08-01`
**Status:** `Draft`
**Author:** `0x-void Dev Team (AI-Assisted)`

---

## 🎯 **1. INTRODUCTION**
This document defines the **development environment**, **tools**, **dependencies**, and **coding conventions** for the *Bike Fitting App* project.

**Goal:** Ensure a **consistent configuration** for the **developer (You, Ziomek)** and the **AI Agent** (Vibe Coding) to **minimize environment issues** and **accelerate development**.

---

## 💻 **2. DEVELOPMENT ENVIRONMENT**

### **2.1 System Requirements**
| **Component**               | **Requirement**                                                                 | **Notes**                                  |
|----------------------------|-----------------------------------------------------------------------------|--------------------------------------------|
| **Operating System**      | Windows 10/11, macOS (Apple Silicon), Linux (Ubuntu 20.04+)               | Android Studio supports all.          |
| **RAM**             | **16GB+** (recommended)                                                   | 8GB minimum (for ARCore).                   |
| **Processor**               | Intel i7 / Ryzen 7 / Apple M1+                                             | Faster APK building.                     |
| **Storage**                   | **SSD** (256GB+ free space)                                            | Faster emulator performance.               |
| **Graphics Card**        | **NVIDIA/AMD/Intel** (for ARCore)                                           | Required for AR emulation.                  |

---

### **2.2 Software**

| **Tool**               | **Version**               | **Purpose**                                                                 | **Link**                                  |
|------------------------|--------------------------|--------------------------------------------------------------------------|-------------------------------------------|
| **Android Studio**         | Giraffe (2022.3.1)       | Main IDE.                                                              | [Download](https://developer.android.com/studio) |
| **JDK**                    | **JDK 17**               | Required for Android Gradle Plugin 8.0+.                                  | [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) |
| **Gradle**                 | **8.0+**                 | Build system.                                                       | -                                         |
| **Git**                    | **2.30+**                | Version control.                                                        | [Git](https://git-scm.com/)               |
| **GitHub Desktop**         | Latest                   | Git GUI (optional).                                               | [GitHub Desktop](https://desktop.github.com/) |
| **Figma**                  | Latest                   | UI/UX design (mockups).                                          | [Figma](https://www.figma.com/)           |
| **ARCore by Google**       | Latest                   | 3D scanning (optional).                                              | [ARCore](https://developers.google.com/ar) |
| **iTextPDF**                | 7.2.5+                   | PDF report generation.                                                | [iTextPDF](https://itextpdf.com/)          |

---

### **2.3 Android Studio Configuration**

#### **2.3.1 Plugins**
| **Plugin**               | **Purpose**                                                                 | **Installation**                          |
|-------------------------|--------------------------------------------------------------------------|-----------------------------------------|
| **Kotlin**              | Kotlin language support.                                               | Built-in.                              |
| **Android Emulator**   | Android device emulation.                                                | Built-in.                              |
| **Google Play Services** | Support for ARCore, Maps, etc.                                           | `Settings > Plugins > Google Play Services` |
| **Hilt**                | Dependency injection (DI).                                                | `Settings > Plugins > Hilt`             |
| **Room**                | Local database.                                                     | Built-in (AndroidX).                   |

#### **2.3.2 Project Setup**
1. **New Project** → **Empty Activity** (Kotlin + Jetpack Compose).
2. **Minimum SDK:** `API 34 (Android 17)`.
3. **Language:** `Kotlin`.
4. **Build System:** `Gradle (KTS)`.
5. **Use Jetpack Compose:** ✅ **Checked**.

---

## 📦 **3. PROJECT STRUCTURE**

```
bike-fitting-app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/void/bikefitting/
│   │   │   │   ├── di/                  # Hilt Modules
│   │   │   │   │   ├── AppModule.kt
│   │   │   │   │   └── RepositoryModule.kt
│   │   │   │   ├── data/               # Data Layer (Clean Architecture)
│   │   │   │   │   ├── local/           # Room Database
│   │   │   │   │   │   ├── dao/         # Data Access Objects
│   │   │   │   │   │   │   ├── MeasurementDao.kt
│   │   │   │   │   │   │   └── ...
│   │   │   │   │   │   ├── entities/     # Room Entities
│   │   │   │   │   │   │   ├── Measurement.kt
│   │   │   │   │   │   │   └── ...
│   │   │   │   │   │   └── BikeFittingDatabase.kt
│   │   │   │   │   ├── remote/          # (Empty in MVP - offline)
│   │   │   │   │   └── repository/     # Repositories
│   │   │   │   │       ├── MeasurementRepository.kt
│   │   │   │   │       └── ...
│   │   │   │   ├── domain/             # Domain Layer
│   │   │   │   │   ├── model/          # Business Models
│   │   │   │   │   │   ├── Measurement.kt
│   │   │   │   │   │   └── ...
│   │   │   │   │   ├── usecase/        # Use Cases (Business Logic)
│   │   │   │   │   │   ├── CalculateSaddleHeight.kt
│   │   │   │   │   │   ├── CalculateHandlebarHeight.kt
│   │   │   │   │   │   └── ...
│   │   │   │   │   └── utils/          # Helpers
│   │   │   │   │       ├── BikeFittingFormulas.kt
│   │   │   │   │       └── Constants.kt
│   │   │   │   ├── presentation/      # Presentation Layer (UI)
│   │   │   │   │   ├── viewmodel/     # ViewModels (MVVM)
│   │   │   │   │   │   ├── GuideViewModel.kt
│   │   │   │   │   │   ├── MeasurementViewModel.kt
│   │   │   │   │   │   └── ...
│   │   │   │   │   ├── screens/        # Compose Screens
│   │   │   │   │   │   ├── guide/       # Guide
│   │   │   │   │   │   │   ├── GuideScreen.kt
│   │   │   │   │   │   │   └── StepDetailScreen.kt
│   │   │   │   │   │   ├── measurement/ # Measurements
│   │   │   │   │   │   │   ├── ManualMeasurementScreen.kt
│   │   │   │   │   │   │   └── ARMeasurementScreen.kt
│   │   │   │   │   │   ├── calculator/  # Calculator
│   │   │   │   │   │   │   └── CalculatorScreen.kt
│   │   │   │   │   │   ├── results/     # Results
│   │   │   │   │   │   │   ├── ResultsScreen.kt
│   │   │   │   │   │   │   └── PdfExportScreen.kt
│   │   │   │   │   │   ├── history/     # History
│   │   │   │   │   │   │   └── HistoryScreen.kt
│   │   │   │   │   │   └── settings/    # Settings
│   │   │   │   │   │       └── SettingsScreen.kt
│   │   │   │   │   ├── components/    # Shared Compose Components
│   │   │   │   │   │   ├── BikeFittingCard.kt
│   │   │   │   │   │   ├── NeonButton.kt
│   │   │   │   │   │   ├── InputField.kt
│   │   │   │   │   │   └── ...
│   │   │   │   │   ├── theme/         # Theme (Cyberpunk)
│   │   │   │   │   │   ├── Color.kt
│   │   │   │   │   │   ├── Theme.kt
│   │   │   │   │   │   └── Type.kt
│   │   │   │   │   └── navigation/    # Navigation
│   │   │   │   │       ├── NavGraph.kt
│   │   │   │   │       ├── AppNavHost.kt
│   │   │   │   │       └── ...
│   │   │   │   ├── di/                  # DI (Hilt)
│   │   │   │   │   └── App.kt          # Main Application Class
│   │   │   │   └── MainActivity.kt     # Entry Point
│   │   │   └── res/                   # Resources
│   │   │       ├── drawable/          # Graphics (SVG, PNG)
│   │   │       │   ├── ic_guide.xml
│   │   │       │   ├── ic_measure.xml
│   │   │       │   └── ...
│   │   │       ├── layout/            # (Empty - Compose)
│   │   │       ├── values/            # Strings, colors, styles
│   │   │       │   ├── colors.xml
│   │   │       │   ├── strings.xml
│   │   │       │   ├── strings-pl.xml  # Polish translations
│   │   │       │   ├── styles.xml
│   │   │       │   └── themes.xml
│   │   │       ├── values-pl/         # Polish translations
│   │   │       │   └── strings.xml
│   │   │       └── font/              # Fonts (optional)
│   │   │           └── jetbrains_mono.ttf
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts              # Module Configuration
├── build.gradle.kts                  # Project Configuration
├── settings.gradle.kts              # Project Settings
├── gradle.properties                 # Gradle Properties
└── README.md                          # Project Documentation
```

---

## 📜 **4. BUILD.GRADLE CONFIGURATION**

### **4.1 Project-level `build.gradle.kts`**
```kotlin
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
}
```

### **4.2 App-level `build.gradle.kts`**
```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt") // For Hilt
}

android {
    namespace = "com.void.bikefitting"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.void.bikefitting"
        minSdk = 34  // Android 17
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Compose
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }

    // Room
    javaCompileOptions {
        annotationProcessorOptions {
            arguments += mapOf(
                "room.schemaLocation" to "$projectDir/schemas",
                "room.incremental" to "true",
                "room.expandProjection" to "true"
            )
        }
    }
}

// Dependencies
dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // AndroidX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Compose
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("androidx.compose.material:material-icons-extended:1.5.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")

    // Navigation (Compose)
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Room
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    kapt("androidx.room:room-compiler:2.6.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // ARCore (Optional)
    implementation("com.google.ar:core:1.40.0")
    implementation("com.google.ar.sceneform:core:1.17.1")
    implementation("com.google.ar.sceneform.ux:sceneform-ux:1.17.1")

    // CameraX
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-camera2:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")

    // iTextPDF
    implementation("com.itextpdf:itext7-core:7.2.5")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.0")
}
```

---

## 🔧 **5. DEPENDENCIES**

| **Library**               | **Version** | **Purpose**                                                                 | **Notes**                                  |
|-----------------------------|------------|--------------------------------------------------------------------------|--------------------------------------------|
| **Kotlin**                  | 1.9.0      | Primary language.                                                           | -                                          |
| **Coroutines**              | 1.7.3      | Asynchronous operations.                                                 | `viewModelScope`, `launch`, `flow`.       |
| **AndroidX Core**          | 1.12.0     | Basic Android functions.                                             | -                                          |
| **Jetpack Compose**         | 1.5.0      | UI Framework.                                                            | Material 3.                               |
| **Compose Navigation**     | 2.7.5      | Navigation between screens.                                                | Hilt integration.                        |
| **Room**                    | 2.6.0      | Local database.                                                     | `Dao`, `Entity`, `Repository`.             |
| **Hilt**                    | 2.48       | Dependency injection (DI).                                                | `@HiltAndroidApp`, `@Module`, `@Inject`.  |
| **ARCore**                  | 1.40.0     | 3D scanning (optional).                                              | Requires Google Play Services.              |
| **CameraX**                 | 1.3.0      | Camera handling.                                                         | `PreviewView`, `CameraSelector`.          |
| **iTextPDF**                | 7.2.5      | PDF generation.                                                        | AGPL license (free for open-source).   |
| **JUnit**                  | 4.13.2     | Unit tests.                                                       | -                                          |
| **Espresso**                | 3.5.1      | UI tests.                                                                | -                                          |

---

## 📝 **6. CODING CONVENTIONS**

### **6.1 Kotlin Style Guide**
- **Naming:**
  - **Classes/Interfaces:** `PascalCase` (e.g., `MeasurementRepository`).
  - **Variables/Methods:** `camelCase` (e.g., `calculateSaddleHeight`).
  - **Constants:** `UPPER_SNAKE_CASE` (e.g., `MAX_SADDLE_HEIGHT`).
  - **Packages:** `com.void.bikefitting.[layer]` (e.g., `com.void.bikefitting.domain.usecase`).
- **Formatting:**
  - **Indentation:** 4 spaces (not tabs).
  - **Line Length:** Max **120 characters**.
  - **Braces:** Opening brace on **same line** (e.g., `fun foo() {`).
  - **Commas:** Always at **end of line** (trailing commas).
- **Documentation:**
  - **Public Classes/Methods:** Always **KDoc** (e.g., `/** ... */`).
  - **Parameters:** Description in KDoc (e.g., `@param height Height in cm`).
  - **Return Values:** Description in KDoc (e.g., `@return Saddle height in mm`).

**Example:**
```kotlin
/**
 * Calculates optimal saddle height based on height.
 * 
 * @param height User height in centimeters.
 * @param bikeType Bike type (GRAVEL or ROAD).
 * @return Saddle height in millimeters.
 */
fun calculateSaddleHeight(height: Int, bikeType: BikeType): Int {
    return when (bikeType) {
        BikeType.GRAVEL -> (height * 0.45).toInt() - 2
        BikeType.ROAD -> (height * 0.45).toInt()
    }
}
```

### **6.2 Jetpack Compose**
- **Component Naming:** `PascalCase` (e.g., `NeonButton`, `MeasurementCard`).
- **Parameters:** Always **`@Composable`** and **immutable** (e.g., `val`, not `var`).
- **Preview:** Always add **`@Preview`** for UI components.
- **Styling:** Use **`MaterialTheme`** (not hardcoded colors).

**Example:**
```kotlin
@Composable
fun NeonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color.Cyan, Color.Magenta)
                )
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        )
    ) {
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun NeonButtonPreview() {
    BikeFittingTheme {
        NeonButton(text = "AR Measurement", onClick = {})
    }
}
```

### **6.3 Room Database**
- **Entities:** Always **`@Entity`** + **`@PrimaryKey`**.
- **DAO:** Interfaces with **`@Dao`** + **`@Query`/`@Insert`/`@Update`/`@Delete`**.
- **Naming:**
  - Tables: **`snake_case`** (e.g., `measurement_history`).
  - Columns: **`snake_case`** (e.g., `saddle_height_mm`).

**Example:**
```kotlin
@Entity(tableName = "measurement_history")
data class MeasurementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userHeightCm: Int,
    val saddleHeightMm: Int,
    val handlebarHeightMm: Int,
    val bikeType: String, // "GRAVEL" or "ROAD"
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface MeasurementDao {
    @Insert
    suspend fun insert(measurement: MeasurementEntity)

    @Query("SELECT * FROM measurement_history ORDER BY timestamp DESC")
    fun getAllMeasurements(): Flow<List<MeasurementEntity>>

    @Query("SELECT * FROM measurement_history WHERE id = :id")
    suspend fun getMeasurementById(id: Long): MeasurementEntity?
}
```

### **6.4 Hilt (Dependency Injection)**
- **Modules:** `@Module` + `@InstallIn` (e.g., `SingletonComponent::class`).
- **Dependencies:** `@Binds` or `@Provides`.
- **Injection:** `@Inject` (constructor) + `@HiltAndroidApp` (application).

**Example:**
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMeasurementRepository(dao: MeasurementDao): MeasurementRepository {
        return MeasurementRepositoryImpl(dao)
    }
}

@HiltAndroidApp
class BikeFittingApp : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    private val viewModel: GuideViewModel by viewModels { viewModelFactory }
}
```

---

## 🧪 **7. TEST ENVIRONMENT**

### **7.1 Emulators**
| **Name**               | **API Level** | **Resolution** | **Purpose**                                  |
|-------------------------|---------------|-------------------|------------------------------------------|
| Pixel 7 Pro             | 34 (Android 17)| 1440x3120         | UI tests (Compose).                       |
| Pixel 5                 | 34            | 1080x2340         | General tests.                            |
| Nexus 5X                | 34            | 1080x1920         | Small screen tests.            |

**Running emulator:**
```bash
# List available emulators
emulator -list-avds

# Run
emulator -avd Pixel_7_Pro_API_34
```

### **7.2 Physical Devices**
| **Model**               | **Android** | **ARCore** | **Purpose**                                  |
|-------------------------|-------------|------------|------------------------------------------|
| Google Pixel 7 Pro      | 17          | ✅ Yes      | AR + performance tests.                    |
| Samsung Galaxy S23      | 17          | ✅ Yes      | Compatibility tests.                   |
| Xiaomi Redmi Note 12    | 17          | ❌ No      | Tests without ARCore.                        |

**Requirements:**
- **ARCore:** Device must be on **support list** ([check here](https://developers.google.com/ar/discover/supported-devices)).
- **Camera:** Resolution **min. 12MP** (for better AR measurements).

---

### **7.3 Automated Tests**

#### **7.3.1 Unit Tests (JUnit)**
- **Location:** `src/test/java/...`
- **Example:**
```kotlin
class BikeFittingFormulasTest {
    @Test
    fun `calculateSaddleHeight for ROAD returns correct value`() {
        // Given
        val height = 180
        val bikeType = BikeType.ROAD

        // When
        val result = calculateSaddleHeight(height, bikeType)

        // Then
        assertEquals(810, result) // 180 * 0.45 = 81
    }

    @Test
    fun `calculateSaddleHeight for GRAVEL returns correct value`() {
        // Given
        val height = 180
        val bikeType = BikeType.GRAVEL

        // When
        val result = calculateSaddleHeight(height, bikeType)

        // Then
        assertEquals(808, result) // 810 - 2mm
    }
}
```

#### **7.3.2 UI Tests (Espresso + Compose)**
- **Location:** `src/androidTest/java/...`
- **Example (Compose):**
```kotlin
@RunWith(AndroidJUnit4::class)
class GuideScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun guideScreen_displaysAllSteps() {
        composeTestRule.setContent {
            BikeFittingTheme {
                GuideScreen()
            }
        }

        // Check if all steps are displayed
        composeTestRule.onAllNodesWithText("Step 1: Saddle Height").assertExists()
        composeTestRule.onAllNodesWithText("Step 2: Saddle Tilt").assertExists()
        // ...
    }
}
```

---

## 🤖 **8. VIBE WORK INTEGRATION (FOR AI AGENT)**

### **8.1 How to Use the AI Agent?**
1. **Ask a question/goal:**
   - **Good:** *"Implement the saddle height calculation use case using the reference table from Section 2.1.3."*
   - **Bad:** *"Write code."*

2. **Provide context:**
   - **Always include:** Relevant documentation, code snippets, error messages.
   - **Reference:** Link to canvases, GitHub repo, or specific files.

3. **Review proposals:**
   - **Check:** Code follows conventions, matches requirements.
   - **Feedback:** Provide clear, specific feedback for improvements.

4. **Iterate:**
   - **Loop:** Continue refining until the solution meets standards.
   - **Test:** Always verify on physical devices before merging.

### **8.2 AI Agent Capabilities**
- ✅ Generate Kotlin code (Jetpack Compose, Room, Hilt, Coroutines).
- ✅ Create architecture diagrams (Mermaid).
- ✅ Write unit tests (JUnit 5, Mockito).
- ✅ Translate documentation (PL ↔ EN).
- ✅ Optimize algorithms and performance.
- ✅ Debug code (analyze logs, stack traces).
- ✅ Generate PDF templates (iTextPDF).
- ❌ Cannot test on physical devices (requires human).
- ❌ Cannot make final decisions (human approval required).

### **8.3 Prompt Examples**

**For Code Generation:**
```
"Create a Jetpack Compose screen for manual bike measurements with fields for:
- Height (cm, number input, validation 100-220)
- Leg length (cm, number input, validation 60-120)
- Bike type (dropdown: Gravel/Road)
- Use the cyberpunk color scheme (#00FFFF, #FF00FF, #0A0A0F)
- Follow the design guidelines from bike-fitting-design-guidelines canvas"
```

**For Debugging:**
```
"The AR measurement screen crashes when scanning. Here's the error:
[ERROR LOG]
Please analyze and propose a fix. Consider:
- ARCore compatibility
- Camera permissions
- Device requirements"
```

**For Architecture:**
```
"Design the data flow for the bike fitting calculator:
- Input: User measurements (height, leg length, etc.)
- Processing: Calculate optimal parameters using reference tables
- Output: Display results and generate PDF
- Use Clean Architecture + MVVM + Hilt
- Show as Mermaid diagram"
```

---

## 📌 **9. TROUBLESHOOTING**

| **Issue**                          | **Solution**                                                                 |
|--------------------------------------|-----------------------------------------------------------------------------|
| **Gradle sync fails** | Check JDK version (must be 17), clean project, invalidate caches.        |
| **ARCore not working** | Verify device compatibility, enable Google Play Services.               |
| **Compose preview not loading** | Ensure all dependencies are correct, restart Android Studio.           |
| **Room database errors** | Check entity definitions, DAO annotations, database version.            |
| **Hilt injection fails** | Verify `@HiltAndroidApp` on Application, `@Inject` on fields.              |
| **PDF export crashes** | Check iTextPDF license, file permissions, storage access.               |

---

**🔹 Signature:**
*Document approved for implementation. Last updated: `2026-08-01`.*
**Stay safe on the Net, Netrunner.** 🚴‍♂️💻