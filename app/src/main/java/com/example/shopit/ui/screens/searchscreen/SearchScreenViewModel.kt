package com.example.shopit.ui.screens.searchscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.example.shopit.data.model.CategoryEntity
import com.example.shopit.data.model.Product
import com.example.shopit.data.repositories.local.LocalDatabaseRepository
import com.example.shopit.data.repositories.remote.RemoteDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchScreenViewModel(
    private val repository: RemoteDatabaseRepository,
    private val localDatabaseRepository: LocalDatabaseRepository,
    private val categoryPager: Pager<Int, CategoryEntity>
):ViewModel() {
    private var _searchResults: MutableStateFlow<List<Product>> = MutableStateFlow(listOf())
    var searchResult = _searchResults.asStateFlow()
    var isSearch: Boolean by mutableStateOf(false)

    private var _categoryList: MutableStateFlow<List<CategoryEntity>> = MutableStateFlow(listOf())
    val categoryList = categoryPager.flow.cachedIn(viewModelScope)

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            Log.d("CATEGORIES", "Getting categories")
//            var categories = localDatabaseRepository.getCategories()
//            _categoryList.value = categories
//            Log.d("CATEGORIES", "Categories: ${_categoryList.value.size}")
//        }
    }


    fun search(searhString: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchResults.value = repository.search(searhString)
                isSearch = true
            } catch (e: Exception){
                _searchResults.value = emptyList<Product>()
            }
            println("SEACH SCREEN STATE: ${_searchResults.value.size}")
        }

    }
}