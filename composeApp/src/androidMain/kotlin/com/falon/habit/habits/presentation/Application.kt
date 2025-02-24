package com.falon.habit.habits.presentation

import android.app.Application
import com.google.firebase.FirebaseApp

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        println("Application firebase")
        FirebaseApp.initializeApp(this)
    }
}
