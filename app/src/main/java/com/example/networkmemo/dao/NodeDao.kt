package com.example.networkmemo.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.networkmemo.data.entities.Node

@Dao
interface NodeDao {
    @Query("INSERT INTO Node (x, y, imgUri, linkUrl, content, description, folder) " +
            "VALUES (:x, :y, :imgUri, :linkUrl, :content, :description, :folder)")
    suspend fun insertNode(
        x: Double,
        y: Double,
        imgUri: String = "",
        linkUrl: String = "",
        content: String = "",
        description: String = "",
        folder: Long = -1
    ): Long

    @Insert
    suspend fun insertNodes(nodes: List<Node>)
    @Update
    suspend fun updateNodes(nodes: List<Node>)
    @Delete
    suspend fun deleteNodes(nodes: List<Node>)
    @Delete
    fun deleteNode(node: Node)
    @Query("SELECT * FROM Node")
    fun getAllNodes(): List<Node>
    @Query("SELECT * FROM Node ORDER BY createdTime DESC LIMIT 1")
    suspend fun getLatestNode(): Node
    @Query("SELECT * FROM Node WHERE id = :id")
    suspend fun getNodeById(id: Long): Node
    @Query("SELECT * FROM Node WHERE folder = :folder")
    fun getNodesByFolder(folder: Long): List<Node>
}