# 🚴‍♂️ **BIKE FITTING APP: DESIGN GUIDELINES**
**Version:** `1.0.0`
**Date:** `2026-08-01`
**Status:** `Draft`
**Author:** `0x-void Dev Team (AI-Assisted)`

---

## 🔴 **1. PROJECT GOAL AND SCOPE**

### **1.1 Main Goal**
A mobile application (Android) that provides an **interactive, step-by-step bike fitting guide** for **gravel/road bikes**, based on **proven methods and industry standards**. 

**Key Assumptions:**
- **Education** - Learning proper bike setup based on the **7-step bike fitting process**. 
- **Practicality** - Supporting **manual measurements** and **automatic measurements (AR)**.
- **Documentation** - Generating **PDF reports** with measurement results.
- **Offline-first** - Full functionality **without internet connection**.

### **1.2 Target Audience**
| **User Type**              | **Skill Level**       | **Needs**                                                                 |
|---------------------------|-----------------------|---------------------------------------------------------------------------|
| Amateur Cyclists          | Beginner             | Simple interface, clear instructions, visual support (AR/images).       |
| Advanced Cyclists         | Intermediate/Advanced | Precise calculations, parameter adjustments, data export.              |
| Mechanics/Service Staff    | Professional          | Quick access to reference tables, repeatable measurements.              |

### **1.3 Functional Scope (MVP)**
✅ **Step-by-step guide** (7 bike fitting steps).
✅ **Manual measurements** (user data entry).
✅ **Automatic measurements (AR)** - Optional body/bike scanning method.
✅ **Parameter calculator** (saddle height, handlebar distance calculations, etc.).
✅ **Results visualization** (summary + diagrams).
✅ **PDF export** (report with measurements and recommendations).
❌ **BLE sensor integration** (excluded from MVP).
❌ **Registration/Login** (not required).
❌ **Ads/Payments** (not applicable to MVP).

---

## 🎨 **2. UI/UX GUIDELINES (CYBERPUNK EDITION)**

### **2.1 Visual Style**
**Inspiration:** *Neon Noir + High-Tech Low-Life*
- **Theme:** **Dark** (dark theme) with **neon accents**.
- **Mood:** *Futuristic, technical, yet readable* - like a **cybernetic bike workshop**.

| **Element**          | **Value**               | **Usage**                          |
|----------------------|---------------------------|------------------------------------|
| **Primary Background** | `#0A0A0F` (almost black)  | Screens, card backgrounds.         |
| **Card Background**   | `#121217` (dark gray)     | Content cards, modals.             |
| **Primary Accent**    | `#00FFFF` (cyan)          | Buttons, active icons, highlights.|
| **Secondary Accent**  | `#FF00FF` (magenta)       | Alerts, errors, highlights.        |
| **Primary Text**      | `#E0E0E0` (light gray)    | Text, descriptions.                |
| **Secondary Text**    | `#8A8A8A` (medium gray)   | Captions, placeholders.            |
| **Success**           | `#00FF88` (green)         | Confirmations, correct measurements.|
| **Error**             | `#FF4444` (red)           | Validation, warnings.             |

### **2.2 Typography**
| **Style**       | **Font**            | **Size** | **Usage**               |
|----------------|----------------------|----------|--------------------------|
| **Headings**    | `Roboto Bold`        | `24sp`   | Screen titles, sections. |
| **Subheadings** | `Roboto Medium`      | `18sp`   | Card sections.          |
| **Text**       | `Roboto Regular`     | `14sp`   | Descriptions, instructions.|
| **Small Text**  | `Roboto Light`       | `12sp`   | Placeholders, captions.  |
| **Code**        | `JetBrains Mono`     | `12sp`   | Measurement values, formulas.|

### **2.3 Icons and Graphics**
- **Icon Style:** *Material Icons* with **glow effect** (neon outline).
- **Icon Colors:**
  - Active: `#00FFFF` (cyan).
  - Inactive: `#4A4A4A`.
- **Graphics:** *Minimalist bike diagrams* (vector, cyberpunk style).
- **Animations:** *Subtle* (e.g., button pulsing, smooth transitions between screens).

### **2.4 UI Components (Jetpack Compose)**

#### **2.4.1 Navigation**
- **Bottom Navigation Bar** (fixed at the bottom of the screen):
  ```
  [Guide] [Calculator] [History] [Settings]
  ```
  - Icons: `📖` (Guide), `🧮` (Calculator), `📊` (History), `⚙️` (Settings).
  - Active element: **Highlighted in cyan (`#00FFFF`)**.

- **Floating Action Button (FAB)**:
  - Position: **Bottom right corner** (above Bottom Nav).
  - Icon: `🔍` (Start Measurement).
  - Color: **Cyan-magenta gradient** (`#00FFFF` → `#FF00FF`).

#### **2.4.2 Cards**
- **Background:** `#121217` with **subtle gradient** (`#1A1A20` → `#0A0A0F`).
- **Border:** `1dp` line in color `#2A2A30`.
- **Shadow:** `elevation = 8dp` (subtle neon reflection).

#### **2.4.3 Buttons**
| **Type**          | **Style**                          | **Usage**               |
|------------------|-----------------------------------|--------------------------|
| **Primary**      | Background: `#00FFFF`, Text: `#0A0A0F`  | Main actions (e.g., "Start Measurement"). |
| **Secondary**    | Background: transparent, Border: `#00FFFF`, Text: `#00FFFF` | Secondary actions. |
| **Ghost**        | Background: transparent, Text: `#E0E0E0` | Tertiary actions. |

#### **2.4.4 Input Fields**
- **Background:** `#121217`.
- **Border:** `1dp` `#2A2A30` (focus: `#00FFFF`).
- **Text:** `#E0E0E0`.
- **Placeholder:** `#8A8A8A`.
- **Validation Error:** Border `#FF4444`.

#### **2.4.5 Step Indicator**
- **Active Step:** Cyan circle (`#00FFFF`) with white text.
- **Completed Step:** Green circle (`#00FF88`) with white text.
- **Inactive Step:** Gray circle (`#2A2A30`) with muted text.

---

## 📱 **3. SCREEN SPECIFIC GUIDELINES**

### **3.1 Splash Screen**
- **Logo:** "🚴 BIKE FITTING" with cyberpunk gradient.
- **Subtitle:** "Optimize Your Ride".
- **Loading:** Progress bar with cyan-magenta gradient.

### **3.2 Welcome Screen**
- **Title:** "Welcome to Bike Fitting".
- **Subtitle:** "Optimize your gravel/road bike position in 7 easy steps".
- **CTA Button:** "Start Now" (Primary).
- **Secondary Button:** "Learn More" (Secondary).

### **3.3 Guide Screen**
- **Step List:** 7 steps with icons and brief descriptions.
- **Step Card:** Expandable with detailed instructions, images, and tips.
- **Progress:** Visual indicator showing current step.

### **3.4 Measurement Screen**
- **Tabs:** Manual / AR.
- **Manual Input:** Form fields for height, inseam, bike type, etc.
- **AR Measurement:** Camera view with overlay guides.
- **Validation:** Real-time feedback on input values.

### **3.5 Results Screen**
- **Summary:** Key parameters in large, highlighted cards.
- **Visualization:** Bike diagram with measurement points.
- **Comparison:** Current vs. recommended values chart.
- **Recommendations:** List of adjustments needed.

### **3.6 History Screen**
- **List:** Previous measurement sessions.
- **Filter:** By date, bike type.
- **Export:** PDF button for each session.

### **3.7 Settings Screen**
- **Language:** Polish / English toggle.
- **Units:** Metric / Imperial.
- **Theme:** Dark mode toggle (default ON).
- **Sounds:** Toggle.

---

## 🎯 **4. ACCESSIBILITY**
- **Color Contrast:** WCAG 2.1 AA compliant.
- **Text Size:** Scalable up to 200%.
- **Touch Targets:** Minimum 48x48dp.
- **Screen Reader:** Full support for TalkBack.

---

**🔹 Signature:**
*Document approved for implementation. Last updated: `2026-08-01`.*
**Stay safe on the Net.** 🚴‍♂️💻