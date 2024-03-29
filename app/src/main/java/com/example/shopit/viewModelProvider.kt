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
import com.example.shopit.ui.screens.temp.TempViewModel

object viewModelProvider {
    val factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(
                remoteDatabaseRepository = shopItApplication().container.remoteDatabaseRepository,
                dataStore = shopItApplication().container.dataStoreInstance,
                localDatabaseRepository = shopItApplication().container.localDatabaseRepository,
                roomPager = shopItApplication().container.roomPager
            )
        }
        initializer {
            TempViewModel()
        }
        initializer {
            ProductScreenViewModel(
                remoteDatabaseRepository = shopItApplication().container.remoteDatabaseRepository,
                localDatabaseRepository = shopItApplication().container.localDatabaseRepository,
            )
        }
        initializer {
            SearchScreenViewModel(
                repository =  shopItApplication().container.remoteDatabaseRepository,
                localDatabaseRepository = shopItApplication().container.localDatabaseRepository,
                categoryPager = shopItApplication().container.categoryPager
            )
        }
        initializer {
            CartScreenViewModel(
                remoteDatabaseRepository = shopItApplication().container.remoteDatabaseRepository,
                apiServiceRepository = shopItApplication().container.apiServiceRepository,
                localDatabaseRepository = shopItApplication().container.localDatabaseRepository
            )

        }
        initializer {
            CheckoutViewModel(shopItApplication().container.remoteDatabaseRepository)
        }
        initializer {
            AuthViewModel(shopItApplication().container.authRepository)
        }
        initializer {
            FavoriteScreenViewModel(
                localDatabaseRepository = shopItApplication().container.localDatabaseRepository,
                favoritesPager = shopItApplication().container.favoritesPager
            )
        }
        initializer {
            SettingsScreenViewModel(shopItApplication().container.dataStoreInstance)
        }
    }

}
fun CreationExtras.shopItApplication():ShopitApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShopitApplication)