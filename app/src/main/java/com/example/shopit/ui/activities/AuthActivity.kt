package com.example.shopit.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopit.ui.screens.LandingScreen
import com.example.shopit.ui.screens.LoginScreen
import com.example.shopit.ui.screens.RegistrationScreen
import com.example.shopit.ui.theme.ShopItTheme
import com.example.shopit.ui.viewmodels.AuthViewModel
import com.example.shopit.viewModelProvider

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopItTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthenticationScreen(
                        onSuccessfulLogin = {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        },
                        onSuccessfulRegistration = {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AuthenticationScreen(
    onSuccessfulLogin:()->Unit,
    onSuccessfulRegistration:()->Unit
) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel(factory = viewModelProvider.factory)
    NavHost(
        navController = navController,
        startDestination = Screens.LANDING_SCREEN.name
    ){
        composable(
            route = Screens.LOGIN_SCREEN.name,
            exitTransition = {
                fadeOut()
//                    slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = TweenSpec(600, easing = EaseInOutQuart))
            },
            enterTransition = {
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = TweenSpec(600, easing = EaseInOutQuart))
            },
        ){
            LoginScreen(
                authViewModel = authViewModel,
                navController = navController,
                onSuccessfullLogin = onSuccessfulLogin
            )
        }
        composable(
            route = Screens.REGISTRATION_SCREEN.name,
            exitTransition = {
                fadeOut()
//                    slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = TweenSpec(600, easing = EaseInOutQuart))
            },
            enterTransition = {
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = TweenSpec(600, easing = EaseInOutQuart))
            },
        ){
            RegistrationScreen(
                navController = navController,
                authViewModel = authViewModel,
                onSuccessfulRegistration = onSuccessfulRegistration
            )
        }
        composable(
            route = Screens.LANDING_SCREEN.name,
            exitTransition = {
                fadeOut()
//                    slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = TweenSpec(600, easing = EaseInOutQuart))
            },
            enterTransition = {
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = TweenSpec(600, easing = EaseInOutQuart))
            },
        ){
            LandingScreen(
                navController = navController
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ShopItTheme {

    }
}