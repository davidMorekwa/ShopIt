package com.example.shopit.repository

import com.example.shopit.fake.FakeDefaultApiServiceRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ApiServiceRespositoryTest {
    private lateinit var apiServiceRepository: FakeDefaultApiServiceRepository
    @Before
    fun before_test(){
        apiServiceRepository = FakeDefaultApiServiceRepository()
    }

    @Test
    fun apiRepositoryService_getAccessToken_verifyToken(){
        runBlocking {
            val expectedToken = "ThisIsASampleAccessToken"
            val actual = apiServiceRepository.getOAuthAccessoken()
        }
    }
//    @Test
//    fun apiRespositoryService_maePayment_verifyPaymentWentThrough(){
//        runBlocking {
//            val expectedCode = "0"
//            var calendar = Calendar.getInstance()
//            var current = LocalDateTime.of(
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH),
//                calendar.get(Calendar.HOUR_OF_DAY),
//                calendar.get(Calendar.MINUTE),
//                calendar.get(Calendar.SECOND)
//            )
//            var request = LipaNaMpesaRequest(
//                partyA = 254115215356,
//                partyB = 174379,
//                phoneNumber = 254115215356,
//                businessShortCode = 174379,
//                password = "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMjMwOTAyMTUyNTI4",
//                timestamp = current.toString(),
//                callBackURL = "https://mydomain.com/path",
//                accountReference = "ShopIt Corporation",
//                amount = 2.0,
//                transactionDesc = "Purchase of goods",
//                transactionType = "CustomerPayBillOnline"
//            )
//            val res = apiServiceRepository.makePayment(request)
//            assertEquals(expectedCode, res.responseCode)
//        }
//    }
}