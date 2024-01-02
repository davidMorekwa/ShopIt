package com.example.shopit.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopit.R
import com.example.shopit.ui.theme.ShopItTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SettingsScreen() {

   Column(
       modifier = Modifier
           .fillMaxSize()
           .padding(8.dp)
   ) {
       Row(
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceAround,
           modifier = Modifier.fillMaxWidth()
       ) {
           Column {
               Icon(
                   imageVector = Icons.Default.Person,
                   contentDescription = "User Icon",
                   modifier = Modifier
                       .size(52.dp)
                       .border(
                           1.dp,
                           color = MaterialTheme.colorScheme.tertiary,
                           shape = RoundedCornerShape(50)
                       )
               )
               Firebase.auth.currentUser?.email?.let { it1 ->
                   Text(
                       text = it1,
                       fontWeight = FontWeight.Bold,
                       fontSize = 13.sp
                   )
               }
           }
           Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Arrow Right")
       }
       Row(
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceAround,
           modifier = Modifier.fillMaxWidth()
       ) {
           Row {
               Icon(
                   painter = painterResource(id = R.drawable.icons8_dark_theme_45___), 
                   contentDescription = "Dark Theme Icon",
                   modifier = Modifier
                       .size(25.dp)
               )
               Text(text = "Dark Theme")
           }
           Switch(
               checked = true,
               onCheckedChange = {
                   TODO("ON SWITCH TOGGLE")
               }
           )
       }

   }

}

@Preview
@Composable
fun SettingsScreenPreview() {
    ShopItTheme {
        SettingsScreen()
    }
}