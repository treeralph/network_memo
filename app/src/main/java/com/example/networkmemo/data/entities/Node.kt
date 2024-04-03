package com.example.networkmemo.data.entities

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.networkmemo.NODE_RADIUS

@Immutable
@Entity(tableName = "Node")
data class Node(
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,
    @ColumnInfo(defaultValue = "0.0")
    var dx: Double = 0.0,
    @ColumnInfo(defaultValue = "0.0")
    var dy: Double = 0.0,
    @ColumnInfo(defaultValue = "0.0")
    var old_dx: Double = 0.0,
    @ColumnInfo(defaultValue = "0.0")
    var old_dy: Double = 0.0,
    @ColumnInfo(defaultValue = "1.0")
    var mass: Double = 1.0,
    @ColumnInfo(defaultValue = "0.0")
    var x: Double = 0.0,
    @ColumnInfo(defaultValue = "0.0")
    var y: Double = 0.0,
    @ColumnInfo(defaultValue = "${NODE_RADIUS * 2}")
    var size: Double = NODE_RADIUS * 2,
    @ColumnInfo(defaultValue = "1.0")
    var weight: Double = 1.0,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    var createdTime: String = "",
    @ColumnInfo(defaultValue = "")
    var imgUri: String = "",
    @ColumnInfo(defaultValue = "")
    var linkUrl: String = "",
    @ColumnInfo(defaultValue = "")
    var content: String = "",
    @ColumnInfo(defaultValue = "")
    var description: String = "",
    @ColumnInfo(defaultValue = "")
    var nodeInfo: String = "",
    @ColumnInfo(defaultValue = "")
    var nodeColor: String = "",
    @ColumnInfo(defaultValue = "-1")
    val folder: Long = -1
)