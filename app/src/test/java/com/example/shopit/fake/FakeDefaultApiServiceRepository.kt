package com.example.shopit.fake

import com.example.shopit.data.remote.darajaApi.ApiServiceRepository

class FakeDefaultApiServiceRepository(): ApiServiceRepository {
    override suspend fun getOAuthAccessoken(): String? {
        return FakeDataSource.response.accessToken
    }

    override suspend fun makePayment(phoneNumber: String, amount: String, token: String) {
        TODO("Not yet implemented")
    }
}