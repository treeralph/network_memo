package com.example.networkmemo.datasource

import com.example.networkmemo.dao.NodeDao
import com.example.networkmemo.data.entities.Node

class NodeLocalSource(private val nodeDao: NodeDao) {
    fun getAllNodes() = nodeDao.getAllNodes()
    fun getNodesByFolder(folderId: Long) = nodeDao.getNodesByFolder(folderId)
    suspend fun insertNodes(nodes: List<Node>) = nodeDao.insertNodes(nodes)
    suspend fun insertNode(
        x: Double,
        y: Double,
        imgUri: String = "",
        linkUrl: String = "",
        content: String = "",
        description: String = "",
        folder: Long = -1
    ): Long = nodeDao.insertNode(x, y, imgUri, linkUrl, content, description, folder)
}
