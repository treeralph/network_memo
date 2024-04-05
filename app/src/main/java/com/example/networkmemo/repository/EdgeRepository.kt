package com.example.networkmemo.repository

import android.app.Application
import com.example.networkmemo.database.AppDatabase
import com.example.networkmemo.dao.NodeDao
import com.example.networkmemo.data.entities.Edge
import com.example.networkmemo.data.entities.Node
import com.example.networkmemo.datasource.EdgeLocalSource
import com.example.networkmemo.datasource.NodeLocalSource

class EdgeRepository(private val edgeSource: EdgeLocalSource) {

    val edges: List<Edge> = edgeSource.getAllEdges()
    suspend fun getAllEdgesByFolder(folderId: Long) = edgeSource.getAllEdgesByFolder(folderId)
    suspend fun getEdgeById(id: Long) = edgeSource.getEdgeById(id)
    suspend fun isEdge(node1: Long, node2: Long) = edgeSource.isEdge(node1, node2)
    fun deleteEdge(edge: Edge) = edgeSource.deleteEdge(edge)
    fun deleteEdgesByNodeId(nodeId: Long) = edgeSource.deleteEdgesByNodeId(nodeId)
    suspend fun insertEdge(
        node1: Long,
        node2: Long,
        folder: Long = -1
    ): Long = edgeSource.insertEdge(node1, node2, folder)
}