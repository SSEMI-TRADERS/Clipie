package com.clipie.main.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clipie.main.domain.repository.ProfileRepository
import com.clipie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {
    var state by mutableStateOf(EditProfileState())
        private set

    fun uploadAvatar() {
        viewModelScope.launch {
            state = state.copy(updateStatus = Resource.Loading)
        }
    }
}