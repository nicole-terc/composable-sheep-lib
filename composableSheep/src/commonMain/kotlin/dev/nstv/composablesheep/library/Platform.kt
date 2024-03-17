package dev.nstv.composablesheep.library

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform