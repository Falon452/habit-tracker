plugins {
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    kotlin("jvm")
}

repositories {
    mavenCentral()
}
