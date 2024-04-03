package com.example.networkmemo.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.networkmemo.database.AppDatabase
import com.example.networkmemo.dao.NodeDao
import com.example.networkmemo.data.entities.Node
import com.example.networkmemo.datasource.NodeLocalSource

class NodeRepository(
    private val nodeSource: NodeLocalSource
) {
    val nodes = nodeSource.getAllNodes()
    suspend fun insertNodes(nodes: List<Node>) = nodeSource.insertNodes(nodes)
    suspend fun insertNode(
        x: Double,
        y: Double,
        imgUri: String = "",
        linkUrl: String = "",
        content: String = "",
        description: String = "",
        folder: Long = -1
    ): Long = nodeSource.insertNode(x, y, imgUri, linkUrl, content, description, folder)
}