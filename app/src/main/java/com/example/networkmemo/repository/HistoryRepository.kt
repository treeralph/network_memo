package com.example.networkmemo.repository

import android.app.Application
import com.example.networkmemo.database.AppDatabase
import com.example.networkmemo.dao.NodeDao
import com.example.networkmemo.data.entities.Edge
import com.example.networkmemo.data.entities.History
import com.example.networkmemo.data.entities.Node
import com.example.networkmemo.datasource.EdgeLocalSource
import com.example.networkmemo.datasource.HistoryLocalSource
import com.example.networkmemo.datasource.NodeLocalSource

class HistoryRepository(private val historySource: HistoryLocalSource) {
    val histories: List<History> = historySource.getAllHistories()
    suspend fun insertHistory(
        latestFolder: Long
    ): Long = historySource.insertHistory(latestFolder)
}