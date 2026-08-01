# 📜 **BIKE FITTING APP: PROJECT REQUIREMENTS**
**Version:** `1.0.0`
**Date:** `2026-08-01`
**Status:** `Draft`
**Author:** `0x-void Dev Team (AI-Assisted)`

---

## 🎯 **1. INTRODUCTION**
This document defines **functional and non-functional requirements** for the mobile application *Bike Fitting App* - a **comprehensive bike fitting tool** for **gravel/road bikes**. 

**Scope:** The application supports a **7-step bike fitting process** for **gravel/road bikes**, with **manual/AR measurements**, **parameter calculator**, and **PDF export**.

**Exclusions (MVP):**
- Integration with BLE sensors (e.g., knee angle sensors).
- User registration/login.
- Advertisements/payments.
- Integration with external platforms (Strava, Garmin).

---

## 📌 **2. FUNCTIONAL REQUIREMENTS**

### **2.1 Main Modules**

#### **🔹 2.1.1 Step-by-Step Guide**
**ID:** `F-001`
**Description:** Interactive guide based on **7 bike fitting steps**, compliant with **industry standard practices**. 

| **Step** | **Name**                          | **Description**                                                                                                                                                                                                 | **Input Data**                          | **Result**                                                                                     |
|----------|------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------|---------------------------------------------------------------------------------------------|
| 1        | **Saddle Height**                | Setting the optimal saddle height based on **height, leg length, bike type (gravel/road)**. Instructions with **illustrations** and **videos (optional)**.                               | Height (cm), leg length (cm), bike type | Saddle height (mm), recommended range ±5mm.                                                   |
| 2        | **Saddle Tilt**              | Adjusting saddle angle using **template + level**. Impact on **perineal pressure, hand/neck comfort**.                                                                             | Saddle type, preferred posture           | Tilt angle (degrees), direction (front/back).                                              |
| 3        | **Saddle Fore-Aft Position**       | Setting saddle in **horizontal axis** (front/back). Depends on **frame geometry** and **riding style** (sporty/touring).                                                                     | Bike type, riding style                     | Saddle position (mm from frame center).                                                        |
| 4        | **Saddle-Handlebar Distance**    | Measuring **sitting length** (distance between saddle and handlebar). Considers **frame reach length**.                                                                                 | Frame length (mm), riding style              | Distance (mm), recommended range ±10mm.                                                      |
| 5        | **Handlebar Height**            | Height difference between **saddle and handlebar** (formula: **A-B=C**). **Reference table** for gravel/road.                                                                          | Saddle height (mm), height (cm)          | Height difference (mm), recommended value from table.                                         |
| 6        | **Cockpit Adjustment**              | Setting **handlebar width** (≈ shoulder width), **hood angle**, **brake/gear lever position**.                                                                         | Shoulder width (cm), handlebar type     | Width (cm), hood angle (degrees).                                                      |
| 7        | **Cleat Positioning**           | Setting **power axis** (metatarsophalangeal joint) **over the pedal axis**.                                                                                                                     | Shoe size (EU), pedal type            | Foot position (mm from front of pedal).                                                       |

**Sources:**
- Reference tables based on **industry-standard bike fitting guidelines** for gravel and road bikes.
- Expert tips from the **cycling industry** (e.g., impact of saddle height on knees and hips).

---

#### **🔹 2.1.2 Measurements**

##### **2.1.2.1 Manual Measurements**
**ID:** `F-002`
**Description:** User enters **measurement data** manually.

| **Field**                     | **Type**       | **Format**       | **Validation**                          | **Example**       |
|------------------------------|----------------|------------------|----------------------------------------|-------------------|
| Height                       | Number        | `cm`             | `100-220`                              | `180`             |
| Leg length (inside leg)    | Number        | `cm`             | `60-120`                              | `85`              |
| Shoulder width             | Number        | `cm`             | `30-60`                               | `42`              |
| Bike type                   | Selection     | `Gravel/Road`    | -                                      | `Gravel`          |
| Current saddle height   | Number        | `mm`             | `500-900`                             | `720`             |
| Current handlebar height| Number        | `mm`             | `500-900`                             | `680`             |
| Saddle type                   | Text         | -                | `Max 50 characters`                        | `SM3`        |
| Handlebar type               | Text         | -                | `Max 50 characters`                        | `Flare 42cm`       |

**Formulas:**
- **Saddle-handlebar height difference:** `C = A (handlebar) - B (saddle)` (mm).
- **Recommended saddle height (gravel):** `Height * 0.45` (approximation from table).
- **Recommended saddle-handlebar distance:** `Frame reach ± 10mm`.

---

##### **2.1.2.2 AR Measurements (Augmented Reality)**
**ID:** `F-003`
**Description:** Automatic measurements using **device camera + ARCore**.

| **Functionality**               | **Description**                                                                                                                                                     | **Technology**          | **Accuracy**       |
|----------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------|----------------------|
| Bike scanning                | Detecting **frame geometry** (saddle height, handlebar, frame angle).                                                                                       | ARCore + CameraX        | ±5mm                 |
| User body scanning     | Measuring **height, leg length, shoulder width** (using **body markers**).                                                                         | ARCore + Pose Detection | ±1cm                 |
| 3D Visualization                  | Overlay on **live camera feed** with **measurement lines** (e.g., line from saddle to pedal).                                                             | ARCore Sceneform        | -                    |
| Calibration                       | Setting **reference points** (e.g., floor, pedal axis).                                                                                            | ARCore Plane Detection | -                    |

**Limitations:**
- Requires **Android 8.0+ (API 26)** and **ARCore-supported device**.
- **Optional feature** - if ARCore is unavailable, hide the option.

---

#### **🔹 2.1.3 Parameter Calculator**
**ID:** `F-004`
**Description:** Calculating **optimal values** based on **input data** and **reference tables**.

| **Parameter**                     | **Formula/Logic**                                                                                                                                                     | **Notes**                          |
|----------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------|
| Saddle height (road)           | Table: `Height (cm) → Height (mm)` (see **Table 1**).                                                                                                           | Standard values for road bikes.     |
| Saddle height (gravel)         | Table: **Road height - 2-3mm** (for better control on technical terrain).                                                                                              | Adjustment for gravel bikes.     |
| Height difference (A-B=C)        | `C = A (handlebar) - B (saddle)`. Comparison with **Table 2**.                                                                                                         | Difference between saddle and handlebar.     |
| Saddle-handlebar distance      | `Reach (frame) ± 10mm` (for gravel: **-5mm** for comfort).                                                                                                             | Depends on riding style.     |
| Handlebar width             | `≈ Shoulder width ± 2cm` (for aerodynamics: **-2cm**; for comfort: **+2cm**).                                                                                     | Matching user preferences.     |
| Saddle tilt angle            | **0° (horizontal)** for most. For **aggressive posture**: **+1° (front)**; for **upright posture**: **-1° (back)**.                                                   | Impact on comfort and stability.     |
| Cleat position                   | **Power axis** (metatarsophalangeal joint) **over the pedal axis**.                                                                                                           | Optimal power transfer.     |

**Table 1: Saddle Height (Road vs. Gravel)**
| **Height (cm)** | **Road (mm)** | **Gravel (mm)** |
|-----------------|---------------|-----------------|
| 150             | 600           | 598             |
| 160             | 660           | 657             |
| 170             | 720           | 717             |
| 180             | 780           | 777             |
| 190             | 840           | 837             |
| 200             | 900           | 897             |

**Table 2: Saddle-Handlebar Height Difference (C = A-B)**
| **Height (cm)** | **Road (mm)** | **Gravel (mm)** |
|-----------------|---------------|-----------------|
| 150             | 50            | 20              |
| 160             | 60            | 30              |
| 170             | 70            | 40              |
| 180             | 81            | 51              |
| 190             | 96            | 66              |
| 200             | 111           | 81              |

---

#### **🔹 2.1.4 Results and Reports**
**ID:** `F-005`
**Description:** Displaying results and generating **PDF report**.

| **Functionality**               | **Description**                                                                                                                                                     |
|----------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Results summary             | Screen with **all calculated parameters** (table + visualization).                                                                                     |
| Visualization                     | **Bike diagram** with marked measurement points (SVG/Canvas).                                                                                         |
| Comparison with recommendations        | **Chart** (e.g., current vs. recommended saddle height).                                                                                                    |
| PDF export                      | Generating **PDF report** with: **input data, results, diagrams, recommendations**.                                                                   |
| Measurement history                | List of **previous sessions** (date, bike type, parameters).                                                                                                  |

**PDF Report Template:**
```
📄 BIKE FITTING REPORT
------------------------
📅 Date: [DD-MM-YYYY]
🚴 Bike Type: [Gravel/Road]
👤 Height: [X] cm | Leg Length: [Y] cm

📊 RESULTS:
- Saddle Height: [A] mm (Recommended: [B] mm)
- Height Difference: [C] mm (Recommended: [D] mm)
- Saddle-Handlebar Distance: [E] mm (Recommended: [F] mm)
- Handlebar Width: [G] cm (Recommended: [H] cm)

🎯 RECOMMENDATIONS:
- [List of adjustments, e.g., "Raise saddle by 5mm"]

📌 NOTES:
- [Expert tips from the bike fitting industry]
```

---

#### **🔹 2.1.5 Settings**
**ID:** `F-006`
**Description:** Application configuration.

| **Option**               | **Type**       | **Values**               | **Default** |
|-------------------------|----------------|---------------------------|--------------|
| Language                   | Selection     | `Polish / English`         | `English`    |
| Units               | Selection     | `Metric (cm/mm) / Imperial`| `Metric`     |
| Dark mode             | Toggle         | `ON/OFF`                  | `ON`         |
| Sounds                 | Toggle         | `ON/OFF`                  | `ON`         |
| Measurement precision   | Slider         | `1-10` (step 1)            | `5`          |

---

### **2.2 Non-Functional Requirements**

| **Category**               | **Requirement**                                                                 | **Priority** | **Justification**                          |
|-----------------------------|-------------------------------------------------------------------------------|--------------|-------------------------------------------|
| **Android Version**          | **Android 17 (API 34)** and above.                                             | ⭐⭐⭐        | Support for new features (ARCore).     |
| **Offline Work**           | **100% functionality** without internet.                                     | ⭐⭐⭐        | Users in the field (bikepacking).      |
| **Languages**                  | **Polish (PL) + English (EN)**.                                           | ⭐⭐⭐        | Target audience (Poland + international).|
| **APK Size**             | **<50MB**.                                                                   | ⭐⭐          | Fast downloading.                        |
| **Startup Time**       | **<2s** to main screen.                                                    | ⭐⭐          | User experience.               |
| **Memory Usage**         | **<100MB RAM** during operation.                                           | ⭐⭐          | Optimization for older devices.    |
| **Accessibility**              | **WCAG 2.1 AA** (contrast, text size).                                  | ⭐           | Inclusivity.                             |
| **Security**           | **No collection of personal data**.                                       | ⭐⭐⭐        | GDPR compliance.                      |
| **Updates**             | **Manual** (via Google Play).                                           | ⭐           | Version control.                          |

---

### **2.3 Technical Requirements (For Developer)**

| **Aspect**               | **Requirement**                                                                 |
|--------------------------|-------------------------------------------------------------------------------|
| **Language**                | **Kotlin** (100% cover).                                                     |
| **UI Framework**         | **Jetpack Compose** (Material 3).                                            |
| **Architecture**          | **MVVM + Clean Architecture** (Use Cases, Repositories).                  |
| **Database**          | **Room Database** (local storage of measurement history).              |
| **AR**                   | **ARCore** (optional).                                                    |
| **Camera**               | **CameraX**.                                                                |
| **PDF**                  | **iTextPDF** or **Android PDF API**.                                        |
| **Dependency Injection** | **Hilt**.                                                                  |
| **Tests**                | **JUnit 5** (unit), **Espresso** (UI).                                      |
| **Build System**         | **Gradle (KTS)**.                                                           |

---

## 📊 **3. PRIORITIZATION (MVP)**

### **🔴 Must-Have (MVP)**
| **ID**   | **Functionality**               | **Complexity** | **Estimated Time** |
|----------|----------------------------------|----------------|-------------------|
| F-001    | Step-by-step guide         | Medium        | 10d              |
| F-002    | Manual measurements                 | Low          | 5d               |
| F-004    | Parameter calculator            | Medium        | 8d               |
| F-005    | Results + PDF export             | Medium        | 7d               |
| -        | Translations (PL/EN)              | Low          | 3d               |
| **TOTAL**|                                  |               | **~33d**         |

### **🟡 Nice-to-Have (Post-MVP)**
| **ID**   | **Functionality**               | **Complexity** | **Estimated Time** |
|----------|----------------------------------|----------------|-------------------|
| F-003    | AR measurements                       | High         | 14d              |
| -        | 3D visualization (bike diagram) | Medium        | 5d               |
| -        | Measurement history                | Low          | 2d               |
| **TOTAL**|                                  |               | **~21d**         |

---

## 🔗 **4. DEPENDENCIES**
- **ARCore:** Requires **Google Play Services** (for AR measurements).
- **CameraX:** Requires **camera permissions** (`CAMERA` permission).
- **Room Database:** Requires **AndroidX**.
- **iTextPDF:** Requires **external library** (AGPL license).

---

## 📝 **5. ATTACHMENTS**
- **Data Source:** Standard reference tables for bike fitting.
- **Reference Tables:** Included in **Section 2.1.3**.
- **UI Inspirations:** Generally accepted standards for cycling application design.

---

**🔹 Signature:**
*Document approved for implementation. Last updated: `2026-08-01`.*
**Stay safe on the Net.** 🚴‍♂️💻