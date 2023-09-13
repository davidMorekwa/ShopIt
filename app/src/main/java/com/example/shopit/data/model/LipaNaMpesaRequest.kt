package com.example.shopit.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LipaNaMpesaRequest(
    @Json(name = "AccountReference")
    var accountReference: String,
    @Json(name = "Amount")
    var amount: Double,
    @Json(name = "BusinessShortCode")
    var businessShortCode: Int,
    @Json(name = "CallBackURL")
    var callBackURL: String,
    @Json(name = "PartyA")
    var partyA: Long,
    @Json(name = "PartyB")
    var partyB: Int,
    @Json(name = "Password")
    var password: String,
    @Json(name = "PhoneNumber")
    var phoneNumber: Long,
    @Json(name = "Timestamp")
    var timestamp: String,
    @Json(name = "TransactionDesc")
    var transactionDesc: String,
    @Json(name = "TransactionType")
    var transactionType: String
)