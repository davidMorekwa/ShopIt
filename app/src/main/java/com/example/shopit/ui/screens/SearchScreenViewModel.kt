package com.example.shopit.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.shopit.data.model.Product
import com.example.shopit.data.remote.RemoteDatabaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchScreenViewModel(private val repository: RemoteDatabaseRepository):ViewModel() {
    private var _searchResults: MutableStateFlow<List<Product>> = MutableStateFlow(listOf())
    var searchResult = _searchResults.asStateFlow()
    var isSearch: Boolean by mutableStateOf(false)
    
    suspend fun search(searhString: String){
         try {
             _searchResults.value = repository.search(searhString)
             isSearch = true
        } catch (e: Exception){
             _searchResults.value = emptyList<Product>()
        }
        println("SEACH SCREEN STATE: ${_searchResults.value.size}")
    }
}