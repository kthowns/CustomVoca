package com.example.customvoca.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Voca::class, VocaList::class], version = 1)
abstract class VocaDatabase : RoomDatabase() {
    abstract fun vocaDao(): VocaDao
    abstract fun vocaListDao(): VocaListDao
}