package com.example.shopit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopit.ui.screens.HomeScreen
import com.example.shopit.ui.screens.HomeScreenViewModel
import com.example.shopit.ui.screens.ProductScreen
import com.example.shopit.ui.screens.ProductScreenViewModel
import com.example.shopit.ui.theme.ShopItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopItTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    ShopItApp()
                }
            }
        }
    }
}
enum class Screens {
    HOMESCREEN,
    PRODUCT_SCREEN
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopItApp() {
    val homeScreenViewModel:HomeScreenViewModel = viewModel(factory = viewModelProvider.factory)
    val productScreenViewModel:ProductScreenViewModel = viewModel(factory = viewModelProvider.factory)
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HOMESCREEN.name
    ){
        composable(
            route = Screens.HOMESCREEN.name,
            exitTransition = {
                fadeOut(
                    animationSpec = TweenSpec(500, easing = EaseInOutQuart)
                )
            },
            enterTransition = {
                fadeIn(
                    animationSpec = TweenSpec(500, easing = EaseInOutQuart)
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = TweenSpec(500, easing = EaseInOutQuart)
                )
            }
        ){
            HomeScreen(
                viewModel = homeScreenViewModel,
                navController = navController
            )
        }
        composable(
            route = Screens.PRODUCT_SCREEN.name,
            exitTransition = {
                fadeOut(
                    animationSpec = TweenSpec(500, easing = EaseInOutQuart)
                )
            },
            enterTransition = {
                fadeIn(
                    animationSpec = TweenSpec(500, easing = EaseInOutQuart)
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = TweenSpec(500, easing = EaseInOutQuart)
                )
            }
        ){
            ProductScreen(
                uiState = homeScreenViewModel.productUiState,
                navController = navController,
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShopItTheme {
        ShopItApp()
    }
}