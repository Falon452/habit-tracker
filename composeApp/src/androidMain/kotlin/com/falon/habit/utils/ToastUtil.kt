package com.falon.habit.utils

import android.widget.Toast
import com.falon.habit.habits.presentation.MyApp

actual fun showToast(message: String) {
    MyApp.Instance?.applicationContext?.let {
        Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
    }
}
