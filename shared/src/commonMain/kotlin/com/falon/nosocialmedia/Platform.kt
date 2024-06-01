package com.falon.nosocialmedia

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform