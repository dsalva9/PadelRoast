# Google Play Store Release Guide

This document outlines the steps required to build and package PadelRoast for release on the Google Play Store.

---

## 1. Release Keystore Configuration

To sign release builds, you must generate a release signing key. Do **not** use the `debug.keystore` located in the root workspace for production.

### Generating a Production Keystore
Execute the following keytool command to generate a release key:

```bash
keytool -genkey -v -keystore release.keystore -alias padelroast-alias -keyalg RSA -keysize 2048 -validity 10000
```
This generates a `release.keystore` file. Keep this file secure and back it up.

---

## 2. Signing Configuration in Gradle

To keep keystore credentials secure and out of version control, configure them in `local.properties` (or a global `gradle.properties` on your development machine).

Add the following to `local.properties`:

```properties
release.keystore.path=/path/to/your/release.keystore
release.keystore.password=your_keystore_password
release.keystore.alias=padelroast-alias
release.keystore.key_password=your_key_password
```

### Gradle Build Config Configuration
The app's `build.gradle.kts` configures signing configs dynamically. Ensure it reads properties from `local.properties`:

```kotlin
signingConfigs {
    create("release") {
        storeFile = file(localProperties.getProperty("release.keystore.path"))
        storePassword = localProperties.getProperty("release.keystore.password")
        keyAlias = localProperties.getProperty("release.keystore.alias")
        keyPassword = localProperties.getProperty("release.keystore.key_password")
    }
}

buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        signingConfig = signingConfigs.getByName("release")
    }
}
```

---

## 3. Code Shrinking & Obfuscation (Proguard)

Release builds enable shrinking via Proguard/R8.
Make sure the following dependencies are kept from obfuscation if parsing errors arise:

* **Moshi / Retrofit:** Enforce keeping reflection models for JSON serialization schemas.
* **Room Database:** Ensure Room classes are retained.

Example rules in `app/proguard-rules.pro`:
```proguard
# Keep Moshi / Retrofit serialization models
-keep class com.example.api.RoastResponseSchema { *; }
-keep class com.example.api.RoastCard { *; }

# Keep Room database schemas
-keep class com.example.data.** { *; }
```

---

## 4. Building & Publishing

### Step 1: Clean and Build Bundle
Always deploy utilizing Android App Bundles (`.aab`) instead of APKs:

```bash
# Clean project build artifacts
./gradlew clean

# Build production app bundle
./gradlew bundleRelease
```

The output `.aab` file will be generated at:
`app/build/outputs/bundle/release/app-release.aab`

### Step 2: Google Play Console Upload
1. Log into your **Google Play Console** account.
2. Select **PadelRoast** -> **Testing / Production**.
3. Create a new release and drag/drop the generated `app-release.aab` file.
4. Roll out to your release track.
