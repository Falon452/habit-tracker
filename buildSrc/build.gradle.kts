plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    // https://github.com/google/dagger/issues/3068
    implementation("com.squareup:javapoet:1.13.0")
}
