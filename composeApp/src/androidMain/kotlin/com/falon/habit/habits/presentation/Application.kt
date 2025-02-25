package com.falon.habit.habits.presentation

import android.app.Application
import com.falon.habit.initKoin
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        initKoin {
            androidContext(this@Application)
            androidLogger()
        }
    }
}
