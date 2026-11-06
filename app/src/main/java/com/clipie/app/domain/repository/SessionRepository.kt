package com.clipie.app.domain.repository

import com.clipie.app.domain.models.User
import com.clipie.util.SessionState
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    val sessionState: Flow<SessionState>

    fun saveSession(user: User)
    fun getSession(): User?
    fun clearSession()
}