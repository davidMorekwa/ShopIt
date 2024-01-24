package com.example.shopit.ui.naivigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopit.data.network.connectionObserver.ConnectivityObserver
import com.example.shopit.ui.screens.Screens
import com.example.shopit.ui.screens.authscreens.AuthViewModel
import com.example.shopit.ui.screens.cartscreen.CartScreen
import com.example.shopit.ui.screens.cartscreen.CartScreenViewModel
import com.example.shopit.ui.screens.favoritesscreen.FavoriteScreenViewModel
import com.example.shopit.ui.screens.homescreen.HomeScreen
import com.example.shopit.ui.screens.homescreen.HomeScreenViewModel
import com.example.shopit.ui.screens.productscreen.ProductScreen
import com.example.shopit.ui.screens.productscreen.ProductScreenViewModel
import com.example.shopit.ui.screens.searchscreen.SearchScreen
import com.example.shopit.ui.screens.searchscreen.SearchScreenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel,
    isActive: Int,
    onLogOutClick: ()-> Unit,
    cartScreenViewModel: CartScreenViewModel,
//    networkStatus: ConnectivityObserver.Status
) {
    val productScreenViewModel: ProductScreenViewModel = viewModel()
    val favoriteScreenViewModel: FavoriteScreenViewModel = viewModel()
    val searchScreenViewModel: SearchScreenViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screens.HOME_SCREEN.name
    ) {

        composable(
            route = Screens.HOME_SCREEN.name,
            exitTransition = {
                fadeOut()
//                    slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = TweenSpec(600, easing = EaseInOutQuart))
            },
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = TweenSpec(600, easing = EaseInOutQuart)
                )
            },
            popEnterTransition = {
                slideInHorizontally(animationSpec = TweenSpec(600, easing = EaseInOutQuart))
            }
        ) {
            HomeScreen(
                homeScreenViewModel = homeScreenViewModel,
                navController = navController,
                isActive = isActive,
                cartScreenViewModel = cartScreenViewModel,
                authViewModel = authViewModel,
                onLogOutClick = onLogOutClick,
                favoriteScreenViewModel = favoriteScreenViewModel,
                productScreenViewModel = productScreenViewModel,
//                networkStatus = networkStatus
            )
        }
        composable(
            route = Screens.PRODUCT_SCREEN.name,
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = TweenSpec(600, easing = EaseInOutQuart)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = TweenSpec(600, easing = EaseInOutQuart)
                )
//                    slideInHorizontally(animationSpec = TweenSpec(600, easing = EaseInOutQuart))
            },
            popEnterTransition = {
                slideInHorizontally(animationSpec = TweenSpec(600, easing = EaseInOutQuart))
            }
        ) {
            ProductScreen(
                navController = navController,
                cartScreenViewModel = cartScreenViewModel,
                productScreenViewModel = productScreenViewModel,
                homeScreenViewModel = homeScreenViewModel
            )
        }
        composable(
            route = Screens.SEARCH_SCREEN.name,
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = TweenSpec(600, easing = EaseInOutQuart)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = TweenSpec(600, easing = EaseInOutQuart)
                )
            },
            popEnterTransition = {
                slideInHorizontally(animationSpec = TweenSpec(600, easing = EaseInOutQuart))
            }
        ) {
            SearchScreen(
                searchScreenViewModel = searchScreenViewModel,
                navController = navController,
                isActive = isActive,
                productScreenViewModel = productScreenViewModel
            )
        }
        composable(
            route = Screens.CART_SCREEN.name,
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = TweenSpec(600, easing = EaseInOutQuart)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = TweenSpec(600, easing = EaseInOutQuart)
                )
            },
            popEnterTransition = {
                slideInHorizontally(animationSpec = TweenSpec(600, easing = EaseInOutQuart))
            }
        ) {
            CartScreen(
                cartScreenViewModel = cartScreenViewModel,
                navController = navController
            )
        }
    }
}