package com.example.networkmemo.datasource

import com.example.networkmemo.dao.EdgeDao
import com.example.networkmemo.dao.HistoryDao
import com.example.networkmemo.dao.NodeDao
import com.example.networkmemo.data.entities.Node

class HistoryLocalSource(private val historyDao: HistoryDao) {
    fun getAllHistories() = historyDao.getAllHistories()
    suspend fun insertHistory(
        latestFolder: Long
    ): Long = historyDao.insertHistory(latestFolder)
}