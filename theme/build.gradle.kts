plugins {
    kotlin("android")
    id("com.android.library")
    alias(libs.plugins.composeCompiler)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.java.get().toInt())
    }
}

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    namespace = "com.falon.habit.theme"

    dependencies {
        implementation(libs.compose.material3)
        implementation(libs.compose.foundation)
        implementation(libs.compose.ui)
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeMultiplatform.get()
    }
}
