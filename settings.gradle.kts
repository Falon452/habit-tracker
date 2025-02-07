enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "habit"
include(":androidApp")
include(":shared")
include(":login")
include(":login:composeApp")
include(":login:shared")
