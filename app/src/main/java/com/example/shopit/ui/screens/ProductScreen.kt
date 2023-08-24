package com.example.shopit.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.shopit.R
import com.example.shopit.Screens
import com.example.shopit.data.model.Product
import com.example.shopit.ui.uiStates.ProductViewUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.suspendCoroutine
import kotlin.math.absoluteValue


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(
    uiState: StateFlow<ProductViewUiState>,
    cartScreenViewModel: CartScreenViewModel,
    navController: NavController
) {
    val uiState = uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icon")
                    }
                },
                modifier = Modifier
                    .height(40.dp)
            )
        },
    ) {
        ProductView(
            uiState = uiState,
            onAddToCartClick = {
                println("PRODUCT $it ADDED TO CART")
//                cartScreenViewModel.addProductToCart(it)
            }
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun ProductView(
    uiState: State<ProductViewUiState>,
    onAddToCartClick: (prodductId: String) -> Unit
) {
    var productQuantity: String by remember {
        mutableStateOf("1")
    }
    var isFavorite by rememberSaveable {
        mutableStateOf(false)
    }
    var images = uiState.value.images
    val scrollState = rememberScrollState()
    Column(
        modifier= Modifier
            .padding(top = 0.dp, end = 8.dp, start = 8.dp, bottom = 52.dp)
            .verticalScroll(scrollState)
    ) {
            var pagerState = rememberPagerState(pageCount = {
                uiState.value.images?.size ?: 0
            })

            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fixed(270.dp),
                contentPadding = PaddingValues(16.dp),
                pageSpacing = 20.dp,
                modifier = Modifier
                    .padding(top = 10.dp)
            ) { page ->
                Surface(
                    shadowElevation = 10.dp,
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(10.dp)
                        .height(270.dp)
                        .graphicsLayer {
                            val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue
                            alpha = 0.5f + 0.5f * (1f - pageOffset.coerceIn(0f, 1f))
                        }
                ) {
                    AsyncImage(model = images?.get(page), contentDescription = "Image")
                    images?.get(page)?.let { Text(text = it.toString()) }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = uiState.value.title.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .weight(2f)
                )
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorites",
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(imageVector = Icons.Rounded.Star, contentDescription = "Rating")
                Text(
                    text = "Product Ratings"
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Description",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp
                )
                Text(text = uiState.value.description.toString())
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "$${uiState.value.price.toString()}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,

                )
                Button(onClick = {
                    TODO("Add to cart from product screen")
                    onAddToCartClick(uiState.value._id!!)
                }) {
                    Text(text = "Add to Cart")
                }
            }
        }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun ProdctScreenPeview() {
    MaterialTheme {

    }
}


val testProduct = Product(
    title = "Temp title",
    main_image = "https://target.scene7.com/is/image/Target/GUEST_d92527bd-8a67-4839-858e-d5555e24ba0e",
    price = 12.0,
    images = "https://target.scene7.com/is/image/Target/GUEST_d92527bd-8a67-4839-858e-d5555e24ba0e | https://target.scene7.com/is/image/Target/GUEST_69997084-6f84-4884-aa15-e0bf18168eb8 | https://target.scene7.com/is/image/Target/GUEST_7b11b2c8-b43c-4838-b2e0-7a4bbfeaaa96 | https://target.scene7.com/is/image/Target/GUEST_7cdcf801-2208-40ea-b39a-ba5680761b24"
)






