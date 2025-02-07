package com.falon.habit.presentation

import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        println("Application firebase")
        FirebaseApp.initializeApp(this)
    }
}
