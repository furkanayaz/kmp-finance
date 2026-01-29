package org.ayaz.spx500

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform