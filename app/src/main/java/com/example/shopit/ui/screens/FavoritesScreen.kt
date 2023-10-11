package com.example.shopit.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shopit.data.model.Product
import com.example.shopit.ui.uiStates.ProductViewUiState


@Composable
fun FavoriteProductItem(
    product: ProductViewUiState,
    onFavoriteProductClick: (product: Product)->Unit
) {
    Surface(
        tonalElevation = 1.dp,
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                TODO("ADD CLICK EVENT")
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = product.images,
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