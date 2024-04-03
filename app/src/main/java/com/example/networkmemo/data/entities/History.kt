package com.example.networkmemo.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,
    @ColumnInfo(defaultValue = "-1")
    val latestFolder: Long = -1
)

