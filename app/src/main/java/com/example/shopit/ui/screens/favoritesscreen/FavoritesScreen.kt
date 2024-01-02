package com.example.shopit.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shopit.ui.uiStates.ProductViewUiState
import com.example.shopit.ui.viewmodels.FavoriteScreenViewModel
import com.example.shopit.ui.viewmodels.ProductScreenViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoriteScreenViewModel: FavoriteScreenViewModel,
    productScreenViewModel: ProductScreenViewModel,
    navController: NavController,
) {
    val uiState = favoriteScreenViewModel.favoriteProducts.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Favorites",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icon")
                    }
                },
                modifier = Modifier
                    .height(35.dp)
            )
        },
    ) {
        if(uiState.value.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(top = 40.dp, start = 8.dp, end = 8.dp, bottom = 50.dp)
                    .fillMaxHeight()
            ) {
                items(uiState.value){ item: ProductViewUiState ->
                    FavoriteProductItem(
                        product = item,
                        onRemoveFromFavorites = {
                            item._id?.let { favoriteScreenViewModel.removeFromFavorites(it) }
                        },
                        onProductClick = {
                            productScreenViewModel.getProduct(item)
                            navController.navigate(Screens.PRODUCT_SCREEN.name)
                        }
                    )
                }
            }
        } else {
            Text(
                text = "No products found :(",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 70.dp)
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
fun FavoriteProductItem(
    product: ProductViewUiState,
    onRemoveFromFavorites: ()->Unit,
    onProductClick: ()->Unit
) {
    Surface(
        tonalElevation = 1.dp,
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onProductClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = product.images?.get(0),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(125.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = product.title.toString())
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "$${product.price.toString()}",
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = {
                            onRemoveFromFavorites()
                        },
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove From favorites")
                    }
                }

            }
            
        }
    }
}