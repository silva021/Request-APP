package com.silva021.myapplication.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.silva021.myapplication.model.Historic

@Dao
interface HistoricDAO {

    @Query("select * from t_Historic  order by id desc limit 20")
    fun getAllHistoric() :List<Historic>

    @Insert
    fun insertHistoric(vararg historic: Historic )
}