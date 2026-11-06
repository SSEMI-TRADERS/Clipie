package com.clipie.app.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.clipie.app.domain.models.User
import com.clipie.app.domain.repository.SessionRepository
import com.clipie.util.SessionState
import com.clipie.util.SharedPreferenceName
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
    private val auth: FirebaseAuth
) : SessionRepository {

    override val sessionState: Flow<SessionState> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val uid = auth.currentUser?.uid

            val state = if (uid != null) {
                SessionState.SignedIn(uid)
            } else {
                SessionState.SignedOut
            }

            trySend(state)
        }

        auth.addAuthStateListener(listener)

        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }

    override fun saveSession(user: User) {
        sharedPreferences.edit {
            putString(
                SharedPreferenceName.USER_SESSION.preferenceName,
                gson.toJson(user)
            )
        }
    }

    override fun getSession(): User? = sharedPreferences
        .getString(SharedPreferenceName.USER_SESSION.preferenceName, null)
        ?.let { userString ->
            gson.fromJson(userString, User::class.java)
        }

    override fun clearSession() {
        sharedPreferences.edit {
            remove(SharedPreferenceName.USER_SESSION.preferenceName)
        }
    }
}