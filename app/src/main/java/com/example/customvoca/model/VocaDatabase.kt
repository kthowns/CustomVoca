package com.example.customvoca.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.customvoca.database.Dic
import com.example.customvoca.database.DicListDao
import com.example.customvoca.database.Word
import com.example.customvoca.database.WordListDao

@Database(entities = [Word::class, Dic::class], version = 1)
abstract class VocaDatabase : RoomDatabase() {
    abstract fun wordListDao(): WordListDao
    abstract fun dicListDao(): DicListDao
    companion object {
        @Volatile
        private var INSTANCE: VocaDatabase? = null
        fun getInstance(context: Context): VocaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VocaDatabase::class.java,
                    "voca_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}