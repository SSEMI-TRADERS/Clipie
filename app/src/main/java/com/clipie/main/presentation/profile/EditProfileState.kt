package com.clipie.main.presentation.profile

import android.net.Uri
import com.clipie.util.Resource

data class EditProfileState(
    val username: String = "",
    val bio: String = "",
    val avatarUrl: String = "",
    val selectedImageUri: Uri? = null,
    val updateStatus: Resource<Unit> = Resource.Idle
)
