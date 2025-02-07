buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.hiltGradlePlugin) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
