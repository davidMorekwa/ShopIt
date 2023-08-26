package com.example.shopit.fake

import com.example.shopit.data.model.Product
import com.example.shopit.rules.TestDispatcherRule
import com.example.shopit.ui.screens.HomeScreenViewModel
import com.example.shopit.ui.uiStates.HomeUiState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeScreenViewModelTest {
    lateinit var repository: FakeDefaultDatabaseRepository
    lateinit var viewModel: HomeScreenViewModel
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun before_test(){
        repository = FakeDefaultDatabaseRepository()
        viewModel = HomeScreenViewModel(repository)
    }
    @Test
    fun homeScreenViewModel_getProducts_verifyHomeScreenUiStateSuccess(){

        runBlocking {
            val expectedUiState = HomeUiState.Success(FakeDataSource.productList)
            val actualUiState = viewModel.homeUiState.value
            assertEquals(expectedUiState, actualUiState)
        }
    }

    @Test
    fun homeScreenViewModel_getProducts_verifyCategories(){
        runBlocking {
            val uiState = HomeUiState.Success(FakeDataSource.productList)
            val actual = viewModel.getCategories(uiState)
            val expected = listOf<String>("All", "Toys", "Electronics")
            assertEquals(expected, actual)
        }
    }
    @Test
    fun HomeScreenVieweModel_filterByCategory_verifyProducts(){
        runBlocking {
            val expected = HomeUiState.Success(FakeDataSource.productList.filter { product: Product -> product.primary_category == "Toys" })
            viewModel.filterProductsByCategory("Toys")
            val actual = viewModel.homeUiState.value
            assertEquals(expected, actual)
        }
    }

}