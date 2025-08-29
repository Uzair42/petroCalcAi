# ⛽ PetroCalc AI – Smart Petrol Efficiency Calculator

![App Icon](https://github.com/Uzair42/petroCalcAi/assets/your-image-here.png)

**PetroCalc AI** is a beautiful, modern Android application built with Kotlin that helps users estimate fuel cost, distance coverage, and consumption based on petrol price, mileage, and refuel amount. It also supports reverse calculations (distance → required fuel) and visualizes fuel history using charts.

---

## 🚀 Features

| Feature                            | Description |
|------------------------------------|-------------|
| 🔢 Fuel & Distance Calculator      | Enter petrol price, mileage, and refill amount to compute how much distance you can travel. |
| 🔄 Reverse Calculator              | Enter a target distance to calculate required liters and total cost. |
| 🌙 Dark Mode Support               | Adapts UI to system or user-defined theme. |
| 📊 Chart Visualization             | Interactive line chart showing historical distance covered per refill. |
| 💾 Save & Load Preferences         | Save mileage and petrol price locally for quick reuse. |
| 📉 Offline Mode & Local Storage    | Works offline and stores history in device preferences. |
| 📱 Fully Responsive UI             | Clean, intuitive UI using fragments and custom styles. |
| 📈 Data Visualization with MPAndroidChart | Fuel history visualized as a line graph with smooth transitions. |

---

## 📲 App Flow

### 🔹 Main Calculator
1. **Input Fields:**
   - Petrol Price (Rs/Liter)
   - Mileage (KM/Liter)
   - Refill Amount (Rs)
2. **Output:**
   - Total Fuel (Liters)
   - Total Distance (KM)
3. ✅ **Save Preferences**: stores inputs for reuse.

### 🔹 Reverse Calculator
1. **Input Fields:**
   - Target Distance (KM)
   - Mileage (KM/Liter)
   - Petrol Price (Rs/Liter)
2. **Output:**
   - Required Liters
   - Estimated Cost (Rs)

### 🔹 Fuel History Chart
- Line chart showing distance covered per refill.
- Fully styled with animation, fill gradient, zoom gestures (optional).
- Opens in a full-screen custom fragment.

---

## 📦 Tech Stack

- **Kotlin**
- **Android Jetpack (Fragments, ViewModel, SharedPreferences)**
- **MPAndroidChart** for charting
- **GSON** for object storage
- **Material Design 3**

---


## 📁 Folder Structure

 ├── MainFragment.kt

 ├── FuelRequiredFragment.kt

 ├── ChartFragment.kt

├── res/

 │ ├── layout/

   │ │ ├── activity_main.xml

   │ │ ├── fragment_main.xml
   
 │ │ └── styles.xml
 
├── drawable/

 │ ├── rounded_edittext.xml
 
├── values/

 │ ├── themes.xml
 



---

## 🔧 How to Build

1. **Clone the repo**
   ``` bash
   git clone https://github.com/Uzair42/petroCalcAi.git
   cd petroCalcAi



 ``` Dependencies

 implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

 implementation ("com.google.code.gson:gson:2.10.1")

```

# MU42 


