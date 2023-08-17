package com.example.shopit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopit.ui.screens.HomeScreen
import com.example.shopit.ui.screens.HomeScreenViewModel
import com.example.shopit.ui.screens.ProductScreen
import com.example.shopit.ui.screens.ProductScreenViewModel
import com.example.shopit.ui.screens.SearchScreen
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
    PRODUCT_SCREEN,
    SEARCH_SCREEN
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopItApp() {
    var isActive by remember {
        mutableStateOf(1)
    }
    val homeScreenViewModel:HomeScreenViewModel = viewModel(factory = viewModelProvider.factory)
    val productScreenViewModel:ProductScreenViewModel = viewModel(factory = viewModelProvider.factory)
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomAppBar(
            onHomeClicked = {
                navController.navigate(Screens.HOMESCREEN.name)
                isActive = 1
                            },
            onSearchClick = {
                navController.navigate(Screens.SEARCH_SCREEN.name)
                isActive = 2
                            },
            onCartClick = {
                          isActive = 3
            },
            isActive = isActive
        )}
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.HOMESCREEN.name
        ){
            composable(
                route = Screens.HOMESCREEN.name,
                exitTransition = {
                    fadeOut()
                },
                enterTransition = {
                    slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = TweenSpec(600, easing = EaseInOutQuart))
                },
                popEnterTransition = {
                    slideInHorizontally(animationSpec = TweenSpec(600, easing = EaseInOutQuart))
                }
            ){
                HomeScreen(
                    viewModel = homeScreenViewModel,
                    navController = navController,
                    isActive = isActive
                )
            }
            composable(
                route = Screens.PRODUCT_SCREEN.name,
                exitTransition = {
                    slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = TweenSpec(600, easing = EaseInOutQuart))
                },
                enterTransition = {
                    slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = TweenSpec(600, easing = EaseInOutQuart))
//                    slideInHorizontally(animationSpec = TweenSpec(600, easing = EaseInOutQuart))
                },
                popEnterTransition = {
                    slideInHorizontally(animationSpec = TweenSpec(600, easing = EaseInOutQuart))
                }
            ){
                ProductScreen(
                    uiState = homeScreenViewModel.productUiState,
                    navController = navController,
                )
            }
            composable(
                route = Screens.SEARCH_SCREEN.name,
                exitTransition = {
                    slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = TweenSpec(600, easing = EaseInOutQuart))
                },
                enterTransition = {
                    slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = TweenSpec(600, easing = EaseInOutQuart))
                },
                popEnterTransition = {
                    slideInHorizontally(animationSpec = TweenSpec(600, easing = EaseInOutQuart))
                }
            ){
                SearchScreen(
                    viewModel = viewModel(factory = viewModelProvider.factory),
                    navController = navController,
                    isActive = isActive,
                    onProductSearchResultClick = {
                        homeScreenViewModel.updateProductUiState(it)
                    }
                )
            }

        }
    }

}
@Composable
fun BottomAppBar(
    onHomeClicked: ()->Unit,
    onSearchClick: ()->Unit,
    onCartClick: ()->Unit,
    isActive:Int
) {
    var homeIcon: ImageVector by remember {
        mutableStateOf(Icons.Default.Home)
    }
    var searchIcon: ImageVector by remember {
        mutableStateOf(Icons.Default.Search)
    }
    var cartIcon: ImageVector by remember {
        mutableStateOf(Icons.Default.ShoppingCart)
    }
    when (isActive) {
        1 -> {
            homeIcon = Icons.Filled.Home
            searchIcon = Icons.Outlined.Search
            cartIcon = Icons.Outlined.ShoppingCart
        }
        2 -> {
            homeIcon = Icons.Outlined.Home
            searchIcon = Icons.Default.Search
            cartIcon = Icons.Outlined.ShoppingCart
        }
        3 -> {
            homeIcon = Icons.Outlined.Home
            searchIcon = Icons.Outlined.Search
            cartIcon = Icons.Filled.ShoppingCart
        }
    }
    androidx.compose.material3.BottomAppBar(
        windowInsets = WindowInsets.navigationBars,
        contentPadding = PaddingValues(horizontal = 2.dp),
        tonalElevation = 2.dp,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = { onHomeClicked() }
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(45.dp)
                ) {

                    Icon(
                        imageVector = homeIcon,
                        contentDescription = "Home Icon",
                        modifier = Modifier
                            .size(27.dp)
                    )
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }
            IconButton(onClick = {
                onSearchClick()
            }) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {

                    Icon(
                        imageVector = searchIcon,
                        contentDescription = "Search Icon",
                        modifier = Modifier
                            .size(27.dp)
                    )
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }
            IconButton(onClick = { onCartClick() }) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {

                    Icon(
                        imageVector = cartIcon,
                        contentDescription = "Home Icon",
                        modifier = Modifier
                            .size(27.dp)
                    )
                    Text(
                        text = "Cart",
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }
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