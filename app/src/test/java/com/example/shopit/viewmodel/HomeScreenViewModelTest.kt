package com.example.shopit.viewmodel

import com.example.shopit.data.model.Product
import com.example.shopit.data.network.connectionObserver.ConnectivityObserver
import com.example.shopit.data.repositories.local.LocalDatabaseRepository
import com.example.shopit.data.repositories.remote.RemoteDatabaseRepository
import com.example.shopit.fake.FakeConnectivityObserver
import com.example.shopit.fake.FakeDataSource
import com.example.shopit.fake.FakeDefaultLocalDatabaseRepository
import com.example.shopit.fake.FakeDefaultRemoteDatabaseRepository
import com.example.shopit.rules.TestDispatcherRule
import com.example.shopit.ui.screens.homescreen.HomeScreenViewModel
import com.example.shopit.ui.screens.homescreen.HomeUiState
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class HomeScreenViewModelTest {
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)

    lateinit var remoteRepository: RemoteDatabaseRepository
    lateinit var localRepository: LocalDatabaseRepository
    lateinit var connectivityObserver: ConnectivityObserver
    lateinit var viewModel: HomeScreenViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun before_test(){
        remoteRepository = FakeDefaultRemoteDatabaseRepository()
        localRepository = FakeDefaultLocalDatabaseRepository()
        connectivityObserver = FakeConnectivityObserver()
//        hiltRule.inject()
        viewModel = HomeScreenViewModel(
            remoteDatabaseRepository = remoteRepository,
            localDatabaseRepository = localRepository,
            connectivityObserver = connectivityObserver
        )
    }
    @Test
    fun homeScreenViewModel_getProducts_verifyHomeScreenUiStateSuccess(){

        runBlocking {
            val expectedUiState = HomeUiState.Success(FakeDataSource.remoteProductList)
            viewModel.getInitialProducts()
            val actualUiState = viewModel.homeUiState.value
            assertEquals(expectedUiState, actualUiState)
        }
    }
    @Test
    fun HomeScreenVieweModel_filterByCategory_verifyProducts(){
        runBlocking {
            val expected = HomeUiState.Success(FakeDataSource.remoteProductList.filter { product: Product -> product.primary_category == "Toys" })
            viewModel.filterProductsByCategory("Toys")
            val actual = viewModel.homeUiState.value
            assertEquals(expected, actual)
        }
    }


}