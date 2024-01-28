package com.example.shopit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val image: String
)