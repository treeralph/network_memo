package com.example.networkmemo.datasource

import com.example.networkmemo.dao.EdgeDao
import com.example.networkmemo.dao.NodeDao
import com.example.networkmemo.data.entities.Edge
import com.example.networkmemo.data.entities.Node

class EdgeLocalSource(private val edgeDao: EdgeDao) {
    fun getAllEdges() = edgeDao.getAllEdges()
    suspend fun getAllEdgesByFolder(folderId: Long) = edgeDao.getEdgesByFolder(folderId)
    suspend fun getEdgeById(id: Long) = edgeDao.getEdgeById(id)
    suspend fun isEdge(node1: Long, node2: Long) = edgeDao.isEdge(node1, node2)
    fun deleteEdge(edge: Edge) = edgeDao.deleteEdge(edge)
    fun deleteEdgesByNodeId(nodeId: Long) = edgeDao.deleteEdgesByNodeId(nodeId)
    suspend fun insertEdge(
        node1: Long,
        node2: Long,
        folder: Long = -1
    ): Long = edgeDao.insertEdge(node1, node2, folder)
}