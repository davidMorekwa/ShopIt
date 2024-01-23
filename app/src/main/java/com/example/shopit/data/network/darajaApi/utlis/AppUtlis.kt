package com.example.shopit.data.network.darajaApi.utlis

import java.util.UUID

object AppUtils {

    fun generateUUID(): String =
        UUID.randomUUID().toString()

    val passKey: String
        get() = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"

}