package com.teledrive.data.secure

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureSessionManager @Inject constructor() {
    private val _decryptedPassword = MutableStateFlow<String?>(null)
    val decryptedPassword: StateFlow<String?> = _decryptedPassword.asStateFlow()
    fun unlock(password: String) { _decryptedPassword.value = password }
    fun lock() { _decryptedPassword.value = null }
    fun isUnlocked(): Boolean = _decryptedPassword.value != null
}
