package com.example.networkmemo.data.entities

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Immutable
@Entity(tableName = "Edge")
data class Edge(
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,
    @ColumnInfo(defaultValue = "-1")
    val node1: Long = 0,
    @ColumnInfo(defaultValue = "-1")
    val node2: Long = 0,
    @ColumnInfo(defaultValue = "1.0")
    val weight: Double = 1.0,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val createdTime: String = "",
    @ColumnInfo(defaultValue = "-1")
    val folder: Long = -1
)
