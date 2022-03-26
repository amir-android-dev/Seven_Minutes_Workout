package com.amir.sevenminutesworkout.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.amir.sevenminutesworkout.room.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDAO {

    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Query("SELECT * FROM `history-table`")
    fun fetchAllHistories(): Flow<List<HistoryEntity>>
}