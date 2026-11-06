package com.clipie.util

sealed class SessionState() {
    class SignedIn(val uid: String) : SessionState()
    object SignedOut : SessionState()
    object Initializing : SessionState()
}