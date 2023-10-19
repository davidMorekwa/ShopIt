package com.example.shopit.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shopit.data.model.Product
import com.example.shopit.ui.viewmodels.SearchScreenViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchScreenViewModel: SearchScreenViewModel,
    navController: NavController,
    onProductSearchResultClick: (product: Product)->Unit,
    isActive: Int,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    var searchValue by rememberSaveable {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val uiState = searchScreenViewModel.searchResult.collectAsState()
    var isSearch = searchScreenViewModel.isSearch
    if (isSearch){
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Search Results for '$searchValue'",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .padding(top = 8.dp)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { searchScreenViewModel.isSearch = false }) {
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
                    items(uiState.value) { item: Product ->
                        SearchProductItem(
                            product = item,
                            onProductSearchResultClick = {
                                onProductSearchResultClick(item)
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
    } else{
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icon")
                    }
                },
                modifier = Modifier
                    .height(35.dp)
            )
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 35.dp, start = 8.dp, end = 8.dp, bottom = 50.dp)
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = searchValue,
                onValueChange = { searchValue = it },
                placeholder = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
                        Text(text = "Search")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    scope.launch {
                        searchScreenViewModel.search(searchValue)
                    }
                }),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .width(320.dp)
            )
            Spacer(modifier = Modifier.height(35.dp))
            Text(
                text = "Recent Search results",
                fontWeight = FontWeight.ExtraBold
            )
        }

    }
}

}

@Composable
fun SearchProductItem(
    product: Product,
    onProductSearchResultClick: (product: Product)->Unit
) {
    Surface(
        tonalElevation = 1.dp,
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductSearchResultClick(product) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = product.main_image,
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(125.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = product.title.toString())
                Text(
                    text = "$${product.price.toString()}",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun SeachScreenPreview() {
    MaterialTheme {
//        SearchScreen()
    }
}