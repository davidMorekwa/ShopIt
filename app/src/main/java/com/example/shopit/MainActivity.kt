package com.example.shopit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopit.ui.screens.HomeScreen
import com.example.shopit.ui.screens.HomeScreenViewModel
import com.example.shopit.ui.screens.SplashScreen
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
    SPLASHSCREEN,
    HOMESCREEN
}
@Composable
fun ShopItApp() {
    val viewModel:HomeScreenViewModel = viewModel(factory = viewModelProvider.factory)
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HOMESCREEN.name
    ){
        composable(route = Screens.HOMESCREEN.name){
            HomeScreen(viewModel = viewModel)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShopItTheme {

    }
}