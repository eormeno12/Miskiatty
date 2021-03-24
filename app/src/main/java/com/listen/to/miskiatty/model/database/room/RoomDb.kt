package com.listen.to.miskiatty.model.database.room

import android.content.Context
import androidx.room.*
import com.listen.to.miskiatty.model.database.converters.RoomConverters
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.ClientDao
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.database.ProductDao

@Database(
    entities = [Product::class,
               Client::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)

abstract class RoomDb: RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getDatabase(context: Context): RoomDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDb::class.java,
                    "miskiatty_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun productDao(): ProductDao
    abstract fun clientDao(): ClientDao
}
