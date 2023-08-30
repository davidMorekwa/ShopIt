package com.example.shopit.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LipaNaMpesaResponse(
    @Json(name = "CheckoutRequestID")
    var checkoutRequestID: String,
    @Json(name = "CustomerMessage")
    var customerMessage: String,
    @Json(name = "MerchantRequestID")
    var merchantRequestID: String,
    @Json(name = "ResponseCode")
    var responseCode: String,
    @Json(name = "ResponseDescription")
    var responseDescription: String
)