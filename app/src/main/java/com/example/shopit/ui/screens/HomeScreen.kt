package com.example.shopit.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopit.R
import com.example.shopit.ui.theme.ShopItTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    Scaffold(
        topBar = { HomeScreenTopBar() },
        bottomBar = { HomeScreenBottomBar() },

    ) {
        Text(text = "Home Screen", modifier = Modifier.padding(top=52.dp))
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
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Image(
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
    Surface(
        tonalElevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(35.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Icon(
                painter = painterResource(id = R.drawable.icons8_home_18___),
                contentDescription = "Home Icon",
                modifier = Modifier
                    .size(24.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.icons8_search_38___),
                contentDescription = "Search Icon",
                modifier = Modifier
                    .size(24.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.icons8_cart_23___),
                contentDescription = "Cart Icon",
                modifier = Modifier
                    .size(24.dp)
            )
        } 
    }
    
}


@Preview
@Composable
fun HomeScreenPreview() {
    ShopItTheme {
        HomeScreen()
    }
}

