package com.example.shopit.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OAuthResponse(
    @Json(name = "access_token")
    var accessToken: String,
    @Json(name = "expires_in")
    var expiresIn: String
)