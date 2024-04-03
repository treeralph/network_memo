package com.example.networkmemo.datasource

import com.example.networkmemo.dao.EdgeDao
import com.example.networkmemo.dao.FolderDao
import com.example.networkmemo.dao.NodeDao
import com.example.networkmemo.data.entities.Node

class FolderLocalSource(private val folderDao: FolderDao) {
    fun getAllFolders() = folderDao.getAllFolders()
    suspend fun insertFolder(
        folderName: String,
        folderInfo: String
    ): Long = folderDao.insertFolder(folderName, folderInfo)
}