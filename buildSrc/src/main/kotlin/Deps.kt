object Deps {

    // COMPOSE
    private const val activityComposeVersion = "1.8.0"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"

    const val composeVersion = "1.6.0"
    const val composeUi = "androidx.compose.ui:ui:$composeVersion"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
    const val composeFoundation = "androidx.compose.foundation:foundation:$composeVersion"
    const val composeMaterial = "androidx.compose.material:material:$composeVersion"
    const val composeIconsExtended = "androidx.compose.material:material-icons-extended:$composeVersion"

    const val firebaseBomVersion = "33.3.0"
    const val firebaseBom = "com.google.firebase:firebase-bom:$firebaseBomVersion"
    const val firebaseUiAuthVersion = "7.2.0"
    const val devGitLiveFirebaseVersion = "2.1.0"
    const val firebaseAuth = "com.google.firebase:firebase-auth"
    const val firebaseUiAuth = "com.firebaseui:firebase-ui-auth:$firebaseUiAuthVersion"
    const val gitLiveFirebaseFireStore = "dev.gitlive:firebase-firestore:${devGitLiveFirebaseVersion}"
    const val gitLiveFirebaseAuth = "dev.gitlive:firebase-auth:${devGitLiveFirebaseVersion}"
    const val googleServicesVersion = "4.4.2"
    const val googleServices = "com.google.gms:google-services:$googleServicesVersion"

    private const val composeNavigationVersion = "2.7.0"
    const val composeNavigation = "androidx.navigation:navigation-compose:$composeNavigationVersion"

    // KOTLIN DATE TIME
    private const val dateTimeVersion = "0.4.0"
    const val kotlinDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:$dateTimeVersion"

    private const val kotlinResultVersion = "2.0.0"
    const val kotlinResult = "com.michael-bull.kotlin-result:kotlin-result:$kotlinResultVersion"

    // HILT
    private const val hiltVersion = "2.48"
    private const val hiltCompilerVersion = "1.0.0"
    const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:$hiltCompilerVersion"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hiltCompilerVersion"

    // GRADLE PLUGINS
    const val kotlinVersion = "2.0.0"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    private const val androidToolsBuildGradle = "8.2.0"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidToolsBuildGradle"

    private const val sqlDelightGradleVersion = "1.5.3"
    const val sqlDelightGradlePlugin = "com.squareup.sqldelight:gradle-plugin:$sqlDelightGradleVersion"

    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"

    const val kotlinSerialization = "1.5.1"
    const val kotlinxSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${kotlinSerialization}"
    const val kotlinxSerializationCore = "org.jetbrains.kotlinx:kotlinx-serialization-core:${kotlinSerialization}"

    // SQLDELIGHT
    private const val sqlDelightVersion = "1.5.4"
    const val sqlDelightRuntime = "com.squareup.sqldelight:runtime:$sqlDelightVersion"
    const val sqlDelightAndroidDriver = "com.squareup.sqldelight:android-driver:$sqlDelightVersion"
    const val sqlDelightNativeDriver = "com.squareup.sqldelight:native-driver:$sqlDelightVersion"
    const val sqlDelightCoroutinesExtensions = "com.squareup.sqldelight:coroutines-extensions:$sqlDelightVersion"

    // TESTING
    private const val jUnitVersion = "4.13.2"
    const val jUnit = "junit:junit:$jUnitVersion"

    private const val testRunnerVersion = "1.5.1"
    const val testRunner = "androidx.test:runner:$testRunnerVersion"

    const val composeTesting = "androidx.compose.ui:ui-test-junit4:$composeVersion"
    const val composeTestManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"

    const val hiltTesting = "com.google.dagger:hilt-android-testing:$hiltVersion"
}
