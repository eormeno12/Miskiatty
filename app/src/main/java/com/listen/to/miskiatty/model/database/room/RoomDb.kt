package com.listen.to.miskiatty.model.database.room

import android.content.Context
import androidx.room.*
import com.listen.to.miskiatty.model.converters.ImageConverters
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.database.ProductDao

@Database(
    entities = [Product::class],
    version = 1
)
@TypeConverters(ImageConverters::class)

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
}
