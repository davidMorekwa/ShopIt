package com.example.shopit.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.compose.rememberNavController
import com.example.shopit.ui.naivigation.NavGraph
import com.example.shopit.ui.screens.Screens
import com.example.shopit.ui.screens.cartscreen.CartScreenViewModel
import com.example.shopit.ui.screens.homescreen.HomeScreenViewModel
import com.example.shopit.ui.theme.ShopItTheme
import com.example.shopit.viewModelProvider
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val networkConnectivityObserver: ConnectivityObserver = NetworkConnectivityObserver(this)

        if (user == null){
            startActivity(Intent(this, AuthActivity::class.java))
        } else {
            setContent {
//                val networkStatus = networkConnectivityObserver.observe().collectAsState(initial = ConnectivityObserver.Status.Available).value
                val homeScreenViewModel: HomeScreenViewModel = viewModel(factory = viewModelProvider.factory)
                var theme = homeScreenViewModel.toggleSwitchState.collectAsState()
                ShopItTheme(
                    useDarkTheme = theme.value
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ShopItApp(
                            onLogOutClick = {
                                startActivity(Intent(this, AuthActivity::class.java))
                            },
                            homeScreenViewModel = homeScreenViewModel,
//                            networkStatus = networkStatus
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopItApp(
    onLogOutClick: ()->Unit,
    homeScreenViewModel: HomeScreenViewModel,
//    networkStatus: ConnectivityObserver.Status
) {
    var isActive by remember {
        mutableStateOf(1)
    }
    val cartScreenViewModel: CartScreenViewModel = viewModel(factory = viewModelProvider.factory)
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomAppBar(
                onHomeClicked = {
                    navController.navigate(Screens.HOME_SCREEN.name)
                    isActive = 1
                },
                onSearchClick = {
                    navController.navigate(Screens.SEARCH_SCREEN.name)
                    isActive = 2
                },
                onCartClick = {
                    navController.navigate(Screens.CART_SCREEN.name)
                    cartScreenViewModel.getProductsInCart()
                    isActive = 3
                },
                isActive = isActive
            )
        }
    ) {
        NavGraph(
            navController = navController,
            homeScreenViewModel = homeScreenViewModel,
            isActive = isActive,
            onLogOutClick = onLogOutClick,
            cartScreenViewModel = cartScreenViewModel,
//            networkStatus = networkStatus
        )
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
                onClick = { onHomeClicked() },
                modifier = Modifier
                    .size(55.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(55.dp)
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
            IconButton(
                onClick = {
                    onSearchClick()
                },
                modifier = Modifier
                    .size(55.dp)
            ) {
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
            IconButton(
                onClick = { onCartClick() },
                modifier = Modifier
                    .size(55.dp)
            ) {
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


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShopItTheme {
//        ShopItApp(
//            onLogOutClick = {}
//        )
    }
}