package com.teledrive.data.secure

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureSettings @Inject constructor(@ApplicationContext private val context: Context) {
    private val prefs by lazy {
        EncryptedSharedPreferences.create(
            "tele_drive_secure_prefs",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    fun saveBoolean(key: String, value: Boolean) = prefs.edit().putBoolean(key, value).apply()
    fun saveInt(key: String, value: Int) = prefs.edit().putInt(key, value).apply()
    fun saveLong(key: String, value: Long) = prefs.edit().putLong(key, value).apply()
    fun saveString(key: String, value: String) = prefs.edit().putString(key, value).apply()
    fun getBoolean(key: String, default: Boolean): Boolean = prefs.getBoolean(key, default)
    fun getInt(key: String, default: Int): Int = prefs.getInt(key, default)
    fun getLong(key: String, default: Long): Long = prefs.getLong(key, default)
    fun getString(key: String, default: String?): String? = prefs.getString(key, default)
}
