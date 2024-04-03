package com.example.networkmemo.repository

import android.app.Application
import com.example.networkmemo.database.AppDatabase
import com.example.networkmemo.dao.NodeDao
import com.example.networkmemo.data.entities.Folder
import com.example.networkmemo.data.entities.Node
import com.example.networkmemo.datasource.FolderLocalSource
import com.example.networkmemo.datasource.NodeLocalSource

class FolderRepository(private val folderSource: FolderLocalSource) {

    val folders: List<Folder> = folderSource.getAllFolders()

    suspend fun insertFolder(
        folderName: String,
        folderInfo: String
    ): Long = folderSource.insertFolder(folderName, folderInfo)
}