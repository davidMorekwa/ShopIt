package com.example.shopit.ui.screens.temp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.shopit.data.model.Product

@Composable
fun TempScreen(
    data: LazyPagingItems<Product>
) {
    Box(modifier = Modifier.fillMaxSize()){
        if (data.loadState.refresh == LoadState.Loading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Text(text = "David Morekwa")
            LazyColumn{
                items(
                    count = data.itemCount,
                    key = data.itemKey{it._id!!},
                    contentType = data.itemContentType { "contentType" }
                ){ index ->
//                    beers[index]?.let { it.name?.let { it1 -> Text(text = it1) } }
//                    Text(text = beers.name)
                    data[index]?.let { Text(text = it.title!!) }
                }
            }
        }
    }
}