package com.example.shopit

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shopit.ui.screens.HomeScreenViewModel
import com.example.shopit.ui.screens.ProductScreenViewModel

object viewModelProvider {
    val factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(shopItApplication().container.databaseRepository)
        }
        initializer {
            ProductScreenViewModel()
        }
    }

}
fun CreationExtras.shopItApplication():ShopitApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShopitApplication)