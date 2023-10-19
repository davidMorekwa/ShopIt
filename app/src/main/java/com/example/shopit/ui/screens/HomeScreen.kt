package com.example.shopit.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shopit.data.model.Product
import com.example.shopit.ui.uiStates.HomeUiState
import com.example.shopit.ui.viewmodels.AuthViewModel
import com.example.shopit.ui.viewmodels.CartScreenViewModel
import com.example.shopit.ui.viewmodels.FavoriteScreenViewModel
import com.example.shopit.ui.viewmodels.HomeScreenViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel,
    navController: NavController,
    cartScreenViewModel: CartScreenViewModel,
    favoriteScreenViewModel: FavoriteScreenViewModel,
    authViewModel: AuthViewModel,
    onLogOutClick: ()->Unit,
    isActive: Int,
    isDarkTheme: Boolean,
    onSwitchToggle: ()-> Unit,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var uiState = homeScreenViewModel.homeUiState.collectAsState()
    var categories = homeScreenViewModel.categoryList.collectAsState()
    val selectedItem = remember { mutableStateOf(menuItems[0].id) }
    var useDarkTheme: Boolean by rememberSaveable {
        mutableStateOf(isDarkTheme)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if(selectedItem.value == 1) "Home" else if(selectedItem.value == 3) "Wishlist" else "Settings",
                        fontSize = 17.sp,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )

                },
                colors = TopAppBarDefaults.smallTopAppBarColors(),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Icon",
                            modifier = Modifier
                                .size(27.dp)
                        )
                    }

                },
                modifier = Modifier
                    .height(40.dp)
            )
        },
    ) {

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .width(258.dp)
                ) {
                    Spacer(Modifier.height(52.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User Icon",
                            modifier = Modifier
                                .size(52.dp)
                        )
                        Firebase.auth.currentUser?.email?.let { it1 ->
                            Text(
                                text = it1,
                                fontWeight = FontWeight.Light,
                                fontSize = 13.sp
                            )
                        }
                    }
                    Spacer(Modifier.height(52.dp))
                    menuItems.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = item.description) },
                            label = { Text(item.title) },
                            selected = item.id == selectedItem.value,
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedItem.value = item.id
                                if (selectedItem.value == 4){
                                    authViewModel.logOut()
                                    onLogOutClick()
                                }
                            },
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Dark Theme")
                        Switch(
                            checked = useDarkTheme,
                            onCheckedChange = {
                                useDarkTheme = it
                                onSwitchToggle()
                            }
                        )
                    }

                }
            },
            content = {
                if(selectedItem.value == 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 45.dp, bottom = 50.dp)
                    ) {
                        when (uiState.value) {
                            is HomeUiState.Error -> ErrorScreen()
                            is HomeUiState.Loading -> LoadingScreen()
                            is HomeUiState.Success -> {
                                if ((uiState.value as HomeUiState.Success).products.isNotEmpty()) {
                                    SuccessScreen(
                                        products = (uiState.value as HomeUiState.Success).products,
                                        viewModel = homeScreenViewModel,
                                        navController = navController,
                                        scope = scope,
                                        cartScreenViewModel = cartScreenViewModel,
                                        categories = categories.value
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            CircularProgressIndicator(
                                                color = MaterialTheme.colorScheme.tertiary
                                            )
                                            Text(text = "Loading...")
                                        }
                                    }
                                }
                            }
                        }

                    }
                } else if (selectedItem.value == 3) {
                    FavoritesScreen(
                        favoriteScreenViewModel = favoriteScreenViewModel,
                        navController = navController,
                    )
                }
            }
        )
    }
}
@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = "An error has occurred when trying to get data! :(")
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            color = Color.Blue
        )
        Text(text = "Loading...")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuccessScreen(
    products: List<Product>,
    categories: List<String>,
    viewModel: HomeScreenViewModel,
    cartScreenViewModel: CartScreenViewModel,
    navController: NavController,
    scope: CoroutineScope,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            items(categories) { category: String ->
                CategoryItem(
                    category = category,
                    onCategoryClick = { viewModel.filterProductsByCategory(category) }
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { item: Product ->
                ProductItem(
                    product = item,
                    onProductClick = {
                        scope.launch {
                            viewModel.updateProductUiState(item)
                            navController.navigate(Screens.PRODUCT_SCREEN.name)
                        }
                    },
                    onAddToCartClick = {
                        scope.launch {
                            cartScreenViewModel.addProductToCart(item)
                        }
                    }
                )

            }
        }
    }
}

@Composable
fun CategoryItem(
    category: String,
    onCategoryClick: (category: String)->Unit,
) {
    OutlinedButton(
        onClick = {
            println("Category $category button clicked")
            onCategoryClick(category)
        }
    ) {
        Text(
            text = category,
        )
    }

}


@Composable
fun ProductItem(
    product: Product,
    onProductClick:(product: Product)-> Unit,
    onAddToCartClick: (product: Product)->Unit,
) {
    var elevation by remember {
        mutableStateOf(0.dp)
    }
    var insteractionSource = remember {
        MutableInteractionSource()
    }
    if(isSystemInDarkTheme()){
        elevation = 3.dp
    } else {
        elevation = 1.dp
    }
    var isAddedToCart = insteractionSource.collectIsPressedAsState().value
    var cartIcon = if (isAddedToCart){
        Icons.Filled.ShoppingCart
    } else {
        Icons.Outlined.ShoppingCart
    }

    val context = LocalContext.current
    Surface(
        tonalElevation = elevation,
//        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .shadow(4.dp, shape = RoundedCornerShape(15.dp))
            .clickable { onProductClick(product) }

    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 5.dp)
        ) {
            AsyncImage(
                model = product.main_image,
                contentDescription = "Product image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .size(120.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(top = 0.dp, end = 8.dp, start = 8.dp, bottom = 5.dp)
            ) {
//                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = product.title.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Ksh." ,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 10.sp
                        )
                        Text(
                            text = "${product.price.toString()}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp
                        )
                    }

                    IconButton(
                        onClick = {
                            onAddToCartClick(product)
                            Toast.makeText(context, "Added to Cart!", Toast.LENGTH_SHORT).show()
                        },
                        interactionSource = insteractionSource
//                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(
                            imageVector = cartIcon,
                            contentDescription = "Add to cart",
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                }
            }

        }
    }
}


//@Preview
//@Composable
//fun HomeScreenPreview() {
//    ShopItTheme {
////        SuccessScreen(products = temp)
//    }
//}
val temp:List<Product> = listOf(
    Product(
        title = "Temp title",
        main_image = "https://target.scene7.com/is/image/Target/GUEST_d92527bd-8a67-4839-858e-d5555e24ba0e",
        price = 12.0
    ),
    Product(
        title = "Temp title",
        main_image = "https://target.scene7.com/is/image/Target/GUEST_d92527bd-8a67-4839-858e-d5555e24ba0e",
        price = 12.0
    ),
    Product(
        title = "Temp title",
        main_image = "https://target.scene7.com/is/image/Target/GUEST_d92527bd-8a67-4839-858e-d5555e24ba0e",
        price = 12.0
    ),
    Product(
        title = "Temp title",
        main_image = "https://target.scene7.com/is/image/Target/GUEST_d92527bd-8a67-4839-858e-d5555e24ba0e",
        price = 12.0
    ),
    Product(
        title = "Temp title",
        main_image = "https://target.scene7.com/is/image/Target/GUEST_d92527bd-8a67-4839-858e-d5555e24ba0e",
        price = 12.0
    ),
    Product(
        title = "Temp title",
        main_image = "https://target.scene7.com/is/image/Target/GUEST_d92527bd-8a67-4839-858e-d5555e24ba0e",
        price = 12.0
    )
)
data class MenuItem(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val description: String
)

val menuItems: List<MenuItem> = listOf(
    MenuItem(
        id = 1,
        title = "Home",
        icon = Icons.Default.Home,
        description = "Home Icon"
    ),
    MenuItem(
        id = 2,
        title = "Settings",
        icon = Icons.Default.Settings,
        description = "Settings Icon"
    ),
    MenuItem(
        id = 3,
        title = "Wishlist",
        icon = Icons.Default.List,
        description = "Cart Icon"
    ),
    MenuItem(
        id = 4,
        title = "LogOut",
        icon = Icons.Default.ExitToApp,
        description = "Logout Icon"
    )
    
)

