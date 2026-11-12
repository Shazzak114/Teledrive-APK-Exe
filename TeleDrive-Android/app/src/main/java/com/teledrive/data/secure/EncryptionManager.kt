package com.teledrive.data.secure

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptionManager @Inject constructor() {
    internal const val ALGORITHM = "AES/GCM/NoPadding"
    internal const val TAG_LENGTH_BIT = 128
    internal const val IV_LENGTH_BYTE = 12
    internal const val SALT_LENGTH_BYTE = 16
    internal const val ITERATION_COUNT = 200_000
    internal const val KEY_LENGTH_BIT = 256

    private fun deriveKey(password: String, salt: ByteArray): SecretKeySpec {
        val spec = PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH_BIT)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val keyBytes = factory.generateSecret(spec).encoded
        return SecretKeySpec(keyBytes, "AES")
    }

    suspend fun encryptFile(inputFile: File, outputFile: File, password: String, onProgress: (Float) -> Unit = {}) {
        val salt = ByteArray(SALT_LENGTH_BYTE)
        SecureRandom().nextBytes(salt)
        val iv = ByteArray(IV_LENGTH_BYTE)
        SecureRandom().nextBytes(iv)
        val key = deriveKey(password, salt)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(TAG_LENGTH_BIT, iv))

        FileOutputStream(outputFile).use { fos ->
            fos.write(salt)
            fos.write(iv)
            CipherOutputStream(fos, cipher).use { cos ->
                inputFile.inputStream().use { fis ->
                    val buffer = ByteArray(65536)
                    var bytesRead = fis.read(buffer)
                    while (bytesRead != -1) {
                        cos.write(buffer, 0, bytesRead)
                        bytesRead = fis.read(buffer)
                    }
                }
            }
        }
    }

    suspend fun decryptFile(encryptedFile: File, outputFile: File, password: String, onProgress: (Float) -> Unit = {}) {
        FileInputStream(encryptedFile).use { fis ->
            val salt = ByteArray(SALT_LENGTH_BYTE)
            fis.read(salt)
            val iv = ByteArray(IV_LENGTH_BYTE)
            fis.read(iv)
            val key = deriveKey(password, salt)
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(TAG_LENGTH_BIT, iv))

            CipherInputStream(fis, cipher).use { cis ->
                FileOutputStream(outputFile).use { fos ->
                    val buffer = ByteArray(65536)
                    var bytesRead = cis.read(buffer)
                    while (bytesRead != -1) {
                        fos.write(buffer, 0, bytesRead)
                        bytesRead = cis.read(buffer)
                    }
                }
            }
        }
    }
}
