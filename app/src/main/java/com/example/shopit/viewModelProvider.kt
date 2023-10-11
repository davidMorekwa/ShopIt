package com.example.shopit

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shopit.ui.viewmodels.AuthViewModel
import com.example.shopit.ui.viewmodels.CartScreenViewModel
import com.example.shopit.ui.viewmodels.CheckoutViewModel
import com.example.shopit.ui.viewmodels.HomeScreenViewModel
import com.example.shopit.ui.viewmodels.ProductScreenViewModel
import com.example.shopit.ui.viewmodels.SearchScreenViewModel

object viewModelProvider {
    val factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(shopItApplication().container.remoteDatabaseRepository)
        }
        initializer {
            ProductScreenViewModel(shopItApplication().container.remoteDatabaseRepository)
        }
        initializer {
            SearchScreenViewModel(shopItApplication().container.remoteDatabaseRepository)
        }
        initializer {
            CartScreenViewModel(shopItApplication().container.remoteDatabaseRepository, shopItApplication().container.apiServiceRepository)
        }
        initializer {
            CheckoutViewModel(shopItApplication().container.remoteDatabaseRepository)
        }
        initializer {
            AuthViewModel(shopItApplication().container.authRepository)
        }
    }

}
fun CreationExtras.shopItApplication():ShopitApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShopitApplication)