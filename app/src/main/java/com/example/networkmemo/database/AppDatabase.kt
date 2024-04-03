package com.example.networkmemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.networkmemo.dao.EdgeDao
import com.example.networkmemo.dao.FolderDao
import com.example.networkmemo.dao.HistoryDao
import com.example.networkmemo.dao.NodeDao
import com.example.networkmemo.data.entities.Edge
import com.example.networkmemo.data.entities.Folder
import com.example.networkmemo.data.entities.History
import com.example.networkmemo.data.entities.Node


@Database(entities = [Node::class, Edge::class, Folder::class, History::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun nodeDao(): NodeDao
    abstract fun edgeDao(): EdgeDao
    abstract fun folderDao(): FolderDao
    abstract fun historyDao(): HistoryDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_db"
                ).build()
            }
        }
    }
}