# Cornerstone Building Brands - Android App Implementation Guide

## Project Structure

```
app/
├── src/
│   └── main/
│       ├── java/com/cornerstone/buildingbrands/
│       │   ├── activities/
│       │   │   ├── MainActivity.java
│       │   │   ├── LoginActivity.java
│       │   │   ├── ScannerActivity.java
│       │   │   ├── ForgotPasswordActivity.java
│       │   │   ├── ProfileActivity.java
│       │   │   └── SettingsActivity.java
│       │   ├── fragments/
│       │   │   ├── DailyProductionFragment.java
│       │   │   ├── HistoryFragment.java
│       │   │   ├── RepairsFragment.java
│       │   │   └── UnitsFragment.java
│       │   ├── adapters/
│       │   │   ├── ScanHistoryAdapter.java
│       │   │   ├── RepairsAdapter.java
│       │   │   └── UnitsAdapter.java
│       │   ├── models/
│       │   │   ├── ScanHistoryItem.java
│       │   │   ├── RepairItem.java
│       │   │   └── UnitItem.java
│       │   ├── utils/
│       │   │   ├── SuccessDialogHelper.java
│       │   │   └── Constants.java
│       │   └── api/
│       │       └── ApiService.java
│       └── res/
│           ├── layout/
│           │   ├── activity_main.xml
│           │   ├── activity_login.xml
│           │   ├── activity_scanner.xml
│           │   ├── activity_forgot_password.xml
│           │   ├── fragment_scan_history.xml
│           │   ├── fragment_daily_production.xml
│           │   ├── fragment_repairs.xml
│           │   ├── fragment_units.xml
│           │   ├── item_scan_history.xml
│           │   ├── item_repair.xml
│           │   ├── item_unit.xml
│           │   ├── dialog_success.xml
│           │   └── dialog_scan_result.xml
│           ├── drawable/
│           │   ├── button_green_rounded.xml
│           │   ├── button_green_light_rounded.xml
│           │   ├── edit_text_background.xml
│           │   ├── card_background.xml
│           │   ├── status_badge_green.xml
│           │   ├── status_badge_orange.xml
│           │   ├── dialog_background_green.xml
│           │   ├── dialog_background_red.xml
│           │   ├── dialog_background_white.xml
│           │   └── ic_*.xml (various icons)
│           ├── anim/
│           │   ├── slide_in_right.xml
│           │   ├── slide_out_left.xml
│           │   ├── fade_in.xml
│           │   ├── fade_out.xml
│           │   ├── scale_up.xml
│           │   └── slide_up.xml
│           ├── menu/
│           │   └── bottom_navigation_menu.xml
│           ├── color/
│           │   └── bottom_nav_selector.xml
│           └── values/
│               ├── colors.xml
│               ├── strings.xml
│               ├── styles.xml
│               └── themes.xml
```

## Implementation Steps

### 1. Create a New Android Project
- Open Android Studio
- Create New Project → Empty Activity
- Package name: `com.cornerstone.buildingbrands`
- Language: Java
- Minimum SDK: API 21 (Android 5.0)

### 2. Add Dependencies
Add the following to your `app/build.gradle`:

```groovy
android {
    viewBinding {
        enabled = true
    }
}

dependencies {
    // Core Android dependencies
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    
    // UI Components
    implementation 'androidx.recyclerview:recyclerview:1.3.1'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'androidx.fragment:fragment:1.6.1'
    
    // Barcode Scanner
    implementation 'me.dm7.barcodescanner:zxing:1.9.13'
    implementation 'com.google.zxing:core:3.5.1'
    
    // Navigation
    implementation 'androidx.navigation:navigation-fragment:2.7.1'
    implementation 'androidx.navigation:navigation-ui:2.7.1'
}
```

### 3. Add Permissions
In `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.INTERNET" />

<application
    android:theme="@style/Theme.CornerstoneBuilding">
    
    <activity
        android:name=".activities.LoginActivity"
        android:theme="@style/Theme.CornerstoneBuilding.NoActionBar">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>
    
    <activity
        android:name=".activities.MainActivity"
        android:theme="@style/Theme.CornerstoneBuilding.NoActionBar" />
    
    <activity
        android:name=".activities.ScannerActivity"
        android:theme="@style/Theme.CornerstoneBuilding.NoActionBar" />
        
    <!-- Add other activities -->
</application>
```

### 4. Create Color Resources
In `res/values/colors.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="primary_green">#27AE60</color>
    <color name="primary_green_light">#B8E6B8</color>
    <color name="primary_dark">#2C3E50</color>
    <color name="text_primary">#2C3E50</color>
    <color name="text_secondary">#7F8C8D</color>
    <color name="text_light">#95A5A6</color>
    <color name="background">#F5F5F5</color>
    <color name="white">#FFFFFF</color>
    <color name="error">#E74C3C</color>
    <color name="warning">#F39C12</color>
    <color name="success">#27AE60</color>
</resources>
```

### 5. Create Theme
In `res/values/themes.xml`:

```xml
<resources>
    <style name="Theme.CornerstoneBuilding" parent="Theme.MaterialComponents.Light.DarkActionBar">
        <item name="colorPrimary">@color/primary_green</item>
        <item name="colorPrimaryVariant">@color/primary_dark</item>
        <item name="colorOnPrimary">@color/white</item>
        <item name="android:statusBarColor">@color/primary_dark</item>
    </style>
    
    <style name="Theme.CornerstoneBuilding.NoActionBar">
        <item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>
    </style>
</resources>
```

### 6. Key Features Implementation

#### Navigation Flow
1. **Login → Main Activity**: Use Intent with slide animation
2. **Main Activity → Scanner**: Start activity with camera permission check
3. **Bottom Navigation**: Fragment transactions with fade animations
4. **Success Dialogs**: Auto-dismiss after 2 seconds with scale animation

#### Data Management
- Use SharedPreferences for login state
- Implement Room database for offline storage (optional)
- Create API service with Retrofit for backend communication

#### Scanner Implementation
- Request camera permission at runtime
- Use ZXing library for barcode scanning
- Display results in custom dialog
- Save scan history to local database

### 7. Testing Checklist
- [ ] Login flow with validation
- [ ] Navigation between all screens
- [ ] Scanner functionality with permissions
- [ ] Success dialog animations
- [ ] Data persistence across app restarts
- [ ] Smooth transitions between activities
- [ ] Bottom navigation state preservation
- [ ] Proper back button handling

### 8. Additional Screens to Implement

Based on the PDF, you'll need to create these additional activities/fragments:
- ForgotPasswordActivity
- ProfileActivity  
- SettingsActivity
- DailyProductionFragment
- RepairsFragment (with job number search)
- UnitsFragment (with date filter)

### 9. Best Practices
- Use ViewBinding for type-safe view access
- Implement proper error handling
- Add loading states for network operations
- Use string resources for all text
- Implement proper lifecycle management
- Add appropriate null checks
- Follow Material Design guidelines

### 10. Icon Resources
You'll need to create or download these vector icons:
- ic_logo (Cornerstone logo)
- ic_box (for Daily Prod)
- ic_history
- ic_camera (for Scanner)
- ic_wrench (for Repairs)
- ic_grid (for Units)
- ic_settings
- ic_profile
- ic_logout

You can use Material Design Icons or create custom vectors in Android Studio's Vector Asset Studio.