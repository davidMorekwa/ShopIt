package com.example.shopit.fake

import com.example.shopit.rules.TestDispatcherRule
import com.example.shopit.ui.screens.HomeScreenViewModel
import com.example.shopit.ui.uiStates.HomeUiState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private lateinit var repository: FakeDefaultDatabaseRepository
class HomeScreenViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()
    @Before
    fun before_test(){
        repository = FakeDefaultDatabaseRepository()
    }
    @Test
    fun homeScreenViewModel_getProducts_verifyHomeScreenUiStateSuccess(){

        runBlocking {
            val viewModel = HomeScreenViewModel(repository)
            val expectedUiState = HomeUiState.Success(FakeDataSource.productList)
            val actualUiState = viewModel.homeUiState.value
            assertEquals(expectedUiState, actualUiState)
        }
    }
}