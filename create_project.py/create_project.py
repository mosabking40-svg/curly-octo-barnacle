import os

# تحديد بنية المجلدات والملفات ومحتوياتها
project_structure = {
    "settings.gradle": """pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "TikTokAutoScroller"
include ':app'""",

    "build.gradle": """plugins {
    id 'com.android.application' version '8.2.0' apply false
    id 'org.jetbrains.kotlin.android' version '1.9.0' apply false
}""",

    "app/build.gradle": """plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'com.musab.autoscroll'
    compileSdk 34

    defaultConfig {
        applicationId "com.musab.autoscroll"
        minSdk 26
        targetSdk 34
        versionCode 1
        versionName "1.0"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
}

dependencies {
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
}""",

    "app/src/main/AndroidManifest.xml": """<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>

    <application
        android:allowBackup="true"
        android:label="Auto Scroller"
        android:supportsRtl="true"
        android:theme="@style/Theme.AppCompat.Light.NoActionBar">
        
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <service
            android:name=".TouchBlockerService"
            android:enabled="true"
            android:exported="false" />

        <service
            android:name=".AutoScrollService"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE"
            android:exported="true">
            <intent-filter>
                <action android:name="android.view.accessibility.AccessibilityService" />
            </intent-filter>
            <meta-data
                android:name="android.view.accessibility.AccessibilityService"
                android:resource="@xml/accessibility_service_config" />
        </service>

    </application>
</manifest>""",

    "app/src/main/java/com/musab/autoscroll/MainActivity.kt": "// ملف MainActivity",
    "app/src/main/java/com/musab/autoscroll/AutoScrollService.kt": "// ملف AutoScrollService",
    "app/src/main/java/com/musab/autoscroll/TouchBlockerService.kt": "// ملف TouchBlockerService",
    "app/src/main/res/layout/activity_main.xml": "",
    "app/src/main/res/xml/accessibility_service_config.xml": ""
}

# تنفيذ عملية الإنشاء تلقائيًا
for file_path, content in project_structure.items():
    # إنشاء المجلدات إذا لم تكن موجودة
    dir_name = os.path.dirname(file_path)
    if dir_name and not os.path.exists(dir_name):
        os.makedirs(dir_name)
    
    # إنشاء الملف وكتابة الكود داخله
    with open(file_path, "w", encoding="utf-8") as f:
        f.write(content)

print("✅ تم إنشاء الهيكل وكتابة الملفات الأساسية بنجاح!")