package com.example.shopit

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shopit.ui.screens.authscreens.AuthViewModel
import com.example.shopit.ui.screens.cartscreen.CartScreenViewModel
import com.example.shopit.ui.screens.cartscreen.CheckoutViewModel
import com.example.shopit.ui.screens.favoritesscreen.FavoriteScreenViewModel
import com.example.shopit.ui.screens.homescreen.HomeScreenViewModel
import com.example.shopit.ui.screens.productscreen.ProductScreenViewModel
import com.example.shopit.ui.screens.searchscreen.SearchScreenViewModel
import com.example.shopit.ui.screens.settingsscreen.SettingsScreenViewModel

object viewModelProvider {
    val factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(
                repository = shopItApplication().container.remoteDatabaseRepository,
                dataStore = shopItApplication().container.dataStoreInstance,
                localDatabaseRepository = shopItApplication().container.localDatabaseRepository,
                context = shopItApplication().applicationContext
            )
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
        initializer {
            FavoriteScreenViewModel(shopItApplication().container.remoteDatabaseRepository)
        }
        initializer {
            SettingsScreenViewModel(shopItApplication().container.dataStoreInstance)
        }
    }

}
fun CreationExtras.shopItApplication():ShopitApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShopitApplication)