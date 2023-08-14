package com.example.shopit.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shopit.R
import com.example.shopit.data.model.Product
import com.example.shopit.ui.theme.ShopItTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {

    var uiState = viewModel.homeUiState.collectAsState()

    Scaffold(
        topBar = { HomeScreenTopBar() },
        bottomBar = { HomeScreenBottomBar() },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 35.dp)
        ){
            when(uiState.value){
                is HomeUiState.Error -> ErrorScreen()
                is HomeUiState.Loading -> LoadingScreen()
                is HomeUiState.Success -> {
                    if((uiState.value as HomeUiState.Success).products.isNotEmpty()){
                        SuccessScreen(products = (uiState.value as HomeUiState.Success).products)
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

    }
}

@Composable
fun HomeScreenTopBar(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(36.dp)
) {
    Surface(
        tonalElevation = 2.dp,
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icons8_menu_38___),
                contentDescription = "Menu Icon",
                modifier = Modifier
                    .size(24.dp)
            )
        }

    }


}

@Composable
fun HomeScreenBottomBar(
    modifier:Modifier = Modifier
        .fillMaxWidth()
) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxWidth()
                .height(45.dp)
        ){
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 3.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_home_36___),
                    contentDescription = "Home Icon",
                    modifier = Modifier
                        .size(27.dp)
                )
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 3.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_search_38___),
                    contentDescription = "Home Icon",
                    modifier = Modifier
                        .size(27.dp)
                )
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 3.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_cart_30___),
                    contentDescription = "Home Icon",
                    modifier = Modifier
                        .size(27.dp)
                )
                Text(
                    text = "Cart",
                    style = MaterialTheme.typography.labelSmall
                )
            }

//            Icon(
//                painter = painterResource(id = R.drawable.icons8_search_38___),
//                contentDescription = "Search Icon",
//                modifier = Modifier
//                    .size(30.dp)
//            )
//            Icon(
//                painter = painterResource(id = R.drawable.icons8_cart_30___),
//                contentDescription = "Cart Icon",
//                modifier = Modifier
//                    .size(30.dp)
//            )
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
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        itemsIndexed(products){
            index,item: Product -> ProductItem(product = item)
        }
    }

}
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

@Composable
fun ProductItem(product: Product) {
    Surface(
        tonalElevation = 1.dp,
//        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .shadow(4.dp, shape = RoundedCornerShape(15.dp))

    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = product.main_image,
                contentDescription = "Product image",
                modifier = Modifier
                    .size(165.dp)
                    .clip(RoundedCornerShape(15.dp))
            )
            Text(
                text = product.title.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "$${product.price.toString()}" ,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.icons8_cart_45___),
                    contentDescription = "Add to cart",
                    modifier = Modifier
                        .size(25.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    ShopItTheme {
        SuccessScreen(products = temp)
    }
}

