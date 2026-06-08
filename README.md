# Modularized Android App

This repository starts from a deliberately small Android baseline: a single-module Jetpack Compose app that renders `Hello World!`.

The first goal is to keep the app easy to run while rebuilding the iOS sample architecture in small, verified steps.

## Current Baseline

* One Android application module: `:app`
* Jetpack Compose + Material 3
* `compileSdk` 36.1 and `targetSdk` 36
* `androidx.core:core-ktx` pinned to `1.17.0` so the project builds with the installed Android SDK 36.1

## Verify

```bash
./gradlew :app:assembleDebug
```
