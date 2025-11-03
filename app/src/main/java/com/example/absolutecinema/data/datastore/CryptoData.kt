package com.example.absolutecinema.data.datastore

import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class CryptoData {
    private val cipher = Cipher.getInstance(TRANSFORMATION)
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }

    private fun getKey(): SecretKey? {
        val existingKey = keyStore.getEntry("secret", null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey
    }

    fun encrypt(bytes: ByteArray): ByteArray {
        // Initializes the cipher in encrypt mode and encrypts data
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(bytes)
        return iv + encrypted
    }

    fun decrypt(bytes: ByteArray): ByteArray? {
        // Extracts IV and decrypts the data
        val iv = bytes.copyOfRange(0, cipher.blockSize)
        val data = bytes.copyOfRange(cipher.blockSize, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        return cipher.doFinal(data)
    }
}