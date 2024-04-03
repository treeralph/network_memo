package com.example.networkmemo.datasource

import com.example.networkmemo.dao.EdgeDao
import com.example.networkmemo.dao.NodeDao
import com.example.networkmemo.data.entities.Node

class EdgeLocalSource(private val edgeDao: EdgeDao) {
    fun getAllEdges() = edgeDao.getAllEdges()
    suspend fun insertEdge(
        node1: Long,
        node2: Long,
        folder: Long = -1
    ): Long = edgeDao.insertEdge(node1, node2, folder)
}