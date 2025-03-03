package com.example.nexaconnect.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexaconnect.data.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    init {
        _user.value = authRepository.getCurrentUser()
    }

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                authRepository.signInWithEmail(email, password)
                    .onSuccess { user ->
                        _user.value = user
                        _authState.value = AuthState.Success
                    }
                    .onFailure { exception ->
                        _authState.value = AuthState.Error(exception.message ?: "Sign in failed")
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Sign in failed")
            }
        }
    }

    fun signUpWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                authRepository.signUpWithEmail(email, password)
                    .onSuccess { user ->
                        _user.value = user
                        _authState.value = AuthState.Success
                    }
                    .onFailure { exception ->
                        _authState.value = AuthState.Error(exception.message ?: "Sign up failed")
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Sign up failed")
            }
        }
    }

    fun signInWithGoogle(account: GoogleSignInAccount) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                authRepository.signInWithGoogle(account)
                    .onSuccess { user ->
                        _user.value = user
                        _authState.value = AuthState.Success
                    }
                    .onFailure { exception ->
                        _authState.value = AuthState.Error(exception.message ?: "Google sign in failed")
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Google sign in failed")
            }
        }
    }

    fun signOut() {
        authRepository.signOut()
        _user.value = null
        _authState.value = AuthState.SignedOut
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                authRepository.sendPasswordResetEmail(email)
                    .onSuccess {
                        _authState.value = AuthState.PasswordResetSent
                    }
                    .onFailure { exception ->
                        _authState.value = AuthState.Error(exception.message ?: "Password reset failed")
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Password reset failed")
            }
        }
    }
}

sealed class AuthState {
    object Loading : AuthState()
    object Success : AuthState()
    object SignedOut : AuthState()
    object PasswordResetSent : AuthState()
    data class Error(val message: String) : AuthState()
}
