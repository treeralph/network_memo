package com.example.networkmemo.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Folder")
data class Folder(
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,
    @ColumnInfo(defaultValue = "")
    val folderName: String = "",
    @ColumnInfo(defaultValue = "")
    val folderInfo: String = "",
    @ColumnInfo(defaultValue = "")
    val algorithm: String = "",
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val createdTime: String = ""
)