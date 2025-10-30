package com.example.absolutecinema.data

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.File
import java.nio.charset.StandardCharsets

class SessionManager(context: Context) {
    var requestToken: String? = null
    var sessionId: String?
        get() {
            return if (file.exists()) {
                encryptedFile.openFileInput().use { input ->
                    input.readBytes().toString(StandardCharsets.UTF_8)
                }
            } else {
                null
            }
        }
        set(value) {
            if (value != null) {
                if (file.exists()) file.delete()
                encryptedFile.openFileOutput().use { output ->
                    output.write(value.toByteArray(StandardCharsets.UTF_8))
                }
            }
        }
    var accountId: Int? = null

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val file = File(context.filesDir, "session_id_secure.txt")

    private val encryptedFile = EncryptedFile.Builder(
        context,
        file,
        masterKey,
        EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
    ).build()
}