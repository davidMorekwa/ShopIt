package com.example.shopit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shopit.ui.theme.ShopItTheme

@Composable
fun LandingScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Hey,",
                fontSize = 17.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                text = "Welcome To ShopIt",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(25.dp))
            Button(
                onClick = {
                    navController.navigate(Screens.REGISTRATION_SCREEN.name)
                },
                modifier = Modifier
                    .width(250.dp)
            ) {
                Text(text = "Get Started")
            }
        }
    }
}

@Preview
@Composable
fun LandingScreenPreview() {
    ShopItTheme {
//        LandingScreen()
    }
}