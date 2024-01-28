package com.example.shopit.ui.screens.homescreen

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.example.shopit.data.model.Product
import com.example.shopit.data.model.ProductEntity
import com.example.shopit.data.network.ConnectivityObserver
import com.example.shopit.ui.screens.Screens
import com.example.shopit.ui.screens.authscreens.AuthViewModel
import com.example.shopit.ui.screens.cartscreen.CartScreenViewModel
import com.example.shopit.ui.screens.favoritesscreen.FavoriteScreenViewModel
import com.example.shopit.ui.screens.favoritesscreen.FavoritesScreen
import com.example.shopit.ui.screens.productscreen.ProductScreenViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel,
    navController: NavController,
    cartScreenViewModel: CartScreenViewModel,
    favoriteScreenViewModel: FavoriteScreenViewModel,
    productScreenViewModel: ProductScreenViewModel,
    authViewModel: AuthViewModel,
    onLogOutClick: ()->Unit,
//    data: LazyPagingItems<Product>,
    isActive: Int,
    modifier: Modifier = Modifier
        .fillMaxSize(),
    connectivityObserver: ConnectivityObserver
) {
    val networkStatus by connectivityObserver.observeConnection().collectAsState(
        initial = ConnectivityObserver.Status.Available
    )
//    when(networkStatus){
//        ConnectivityObserver.Status.Unavailable -> {
//            homeScreenViewModel.networkUnavailable()
//        }
//        ConnectivityObserver.Status.Available -> {
//            homeScreenViewModel.getInitialProducts()
//        }
//        ConnectivityObserver.Status.Lost -> {
//            TODO("Implement functionality when connection is lost")
//        }
//        ConnectivityObserver.Status.Losing -> {
//            TODO("Implement functionality when connection is losing")
//        }
//    }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
//    val pagedProducts = homeScreenViewModel.pagedProducts.collectAsLazyPagingItems()
//    var categories = homeScreenViewModel.categoryList.collectAsState()

    val selectedItem = remember { mutableStateOf(menuItems[0].id) }
    var themeUiState = homeScreenViewModel.toggleSwitchState.collectAsState()
//    var uiState = homeScreenViewModel.homeUiState.collectAsState()
    var uiState = homeScreenViewModel.res.collectAsLazyPagingItems()


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
                    windowInsets = WindowInsets(
                        bottom = 50.dp,
                    ),
                    modifier = Modifier
                        .width(258.dp)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(52.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
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
                            checked = themeUiState.value,
                            onCheckedChange = {
                                homeScreenViewModel.changeTheme(it)
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
//                        PagedDataScreen(
//                            products = pagedProducts,
//                            viewModel = homeScreenViewModel,
//                            navController = navController,
//                            cartScreenViewModel = cartScreenViewModel,
//                            productScreenViewModel = productScreenViewModel,
//                            categories = categories.value
//                        )
                        if (uiState.loadState.refresh == LoadState.Loading){
                            LoadingScreen()
                        } else {
                            SuccessScreen(
                                products = uiState,
                                categories = listOf("Toys", "Electronics"),
                                viewModel = homeScreenViewModel,
                                navController = navController,
                                cartScreenViewModel = cartScreenViewModel,
                                productScreenViewModel = productScreenViewModel,
                            )
                        }
//                        when (uiState.loadState) {
//                            HomeUiState.Error -> ErrorScreen(
//                                onRetryClicked = { homeScreenViewModel.getInitialProducts() }
//                            )
//                            LoadState.Loading -> LoadingScreen()
//                            is HomeUiState.Success -> {
//                                if ((uiState.value as HomeUiState.Success).products.isNotEmpty()) {
//                                    SuccessScreen(
//                                        products = (uiState.value as HomeUiState.Success).products,
//                                        viewModel = homeScreenViewModel,
//                                        navController = navController,
//                                        cartScreenViewModel = cartScreenViewModel,
//                                        productScreenViewModel = productScreenViewModel,
//                                        categories = categories.value
//                                    )
//                                } else {
//                                    Box(
//                                        modifier = Modifier.fillMaxSize(),
//                                        contentAlignment = Alignment.Center
//                                    ) {
//                                        Column(
//                                            horizontalAlignment = Alignment.CenterHorizontally
//                                        ) {
//                                            CircularProgressIndicator(
//                                                color = MaterialTheme.colorScheme.tertiary
//                                            )
//                                            Text(text = "Loading...")
//                                        }
//                                    }
//                                }
//                            }
//                        }

                    }
                }
                else if (selectedItem.value == 3) {
                    favoriteScreenViewModel.getFavoriteProducts()
                    FavoritesScreen(
                        favoriteScreenViewModel = favoriteScreenViewModel,
                        navController = navController,
                        productScreenViewModel = productScreenViewModel
                    )
                }
            },
        )
    }
}
@Composable
fun ErrorScreen(
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = "An error has occurred when trying to get data! :(")
        Button(onClick = onRetryClicked) {
            Text(text = "Retry")
        }
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
    products: LazyPagingItems<ProductEntity>,
    categories: List<String>,
    viewModel: HomeScreenViewModel,
    cartScreenViewModel: CartScreenViewModel,
    productScreenViewModel: ProductScreenViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    val gridState = rememberLazyGridState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = gridState
        ) {
            items(
                count = products.itemCount,
                key = products.itemKey{it.id!!},
                contentType = products.itemContentType { "contentType" }
            ) { index ->
                products[index]?.let {
                    ProductItem(
                        product = it,
                        onProductClick = {
                            productScreenViewModel.getProduct(it.toProductViewUiState(it))
                            navController.navigate(Screens.PRODUCT_SCREEN.name)
                        },
                        onAddToCartClick = {
                            cartScreenViewModel.addProductToCart(it)
                        }
                    )
                }

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
    product: ProductEntity,
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
    var isAddedToCart by rememberSaveable {
        mutableStateOf(false)
    }
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
            .clickable { onProductClick(product.toDomainProduct()) }

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
                            onAddToCartClick(product.toDomainProduct())
                            isAddedToCart = true
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
        title = "Favorites",
        icon = Icons.Default.Favorite,
        description = "Favorites Icon"
    ),
    MenuItem(
        id = 4,
        title = "LogOut",
        icon = Icons.Default.ExitToApp,
        description = "Logout Icon"
    )
    
)

