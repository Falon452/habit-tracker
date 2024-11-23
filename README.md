# Setup
1. SetUp Java 17 (Settings -> Build.. -> Build Tools -> Gradle -> Gradle JDK)
2. use kdoctor (brew install kdoctor  kdoctor)

My installation worked with ruby 3.3.6. Used rbenv

iosApp uses CocoaPods version manager


# Some info
Project modules:
1. androidApp module for Android app
2. iosApp module for iOS app
3. shared module for shared logic between iOS and Android
4. buildSrc module with external dependencies versions


Project dependencies:
1. androidApp consumes shared.androindMain and shared.commonMain 
2. iosApp consumes shared.iosMain and shared.commonMain
