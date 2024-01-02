package com.example.shopit.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShopitDatabase: RoomDatabase() {
    abstract fun productsDao():ProductsDao

    companion object{
        @Volatile
        var instance: ShopitDatabase? = null
        fun getDatabase(context: Context):ShopitDatabase{
            return instance?: synchronized(this){
                Room.databaseBuilder(context = context, klass = ShopitDatabase::class.java, name="database_shopit")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}