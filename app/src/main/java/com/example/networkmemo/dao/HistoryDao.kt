package com.example.networkmemo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.example.networkmemo.data.entities.History

@Dao
interface HistoryDao {
    @Query("SELECT * FROM History")
    fun getAllHistories(): List<History>
    @Update
    fun updateHistory(history: History)
    @Query("INSERT INTO History(latestFolder) VALUES (:latestFolder)")
    fun insertHistory(latestFolder: Long): Long
    @Delete
    fun deleteHistory(history: History)
    @Query("DELETE FROM History")
    fun deleteAllHistories()
}