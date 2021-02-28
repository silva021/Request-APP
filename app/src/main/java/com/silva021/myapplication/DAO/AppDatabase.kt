package com.silva021.myapplication.DAO

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.silva021.myapplication.model.Historic

@Database(entities = [Historic::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        fun getInstanceDataBase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "BDAppRequest").build()
    }

    abstract fun historyDao(): HistoricDAO
}