package com.example.absolutecinema.data

import com.example.absolutecinema.network.AuthApiService
import org.json.JSONObject

class AppRepository(private val api: AuthApiService) {
        var refreshToken: String? = null
            private set

        suspend fun fetchToken(): String {
            val response = api.getRequestToken()
            var requestToken: String = ""
            if (response.isSuccessful) {
                val json = response.body()?.string()
                val jsonObject = JSONObject(json)
                requestToken = jsonObject.getString("request_token")
            }
            return requestToken
        }
}