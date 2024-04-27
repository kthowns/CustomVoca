package com.example.customvoca.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.customvoca.database.Dic
import com.example.customvoca.database.DicListDao
import com.example.customvoca.database.Word
import com.example.customvoca.database.WordListDao

@Database(entities = [Word::class, Dic::class], version = 1)
abstract class VocaDatabase : RoomDatabase() {
    abstract fun wordListDao(): WordListDao
    abstract fun dicListDao(): DicListDao
}