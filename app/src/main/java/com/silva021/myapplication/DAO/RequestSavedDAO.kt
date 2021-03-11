package com.silva021.myapplication.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.silva021.myapplication.model.RequestSaved

@Dao
interface RequestSavedDAO {
    @Query("select * from t_Request_Saved order by id asc")
    fun getAllRequestSaved() : List<RequestSaved>

    @Insert
    fun insertRequest(request: RequestSaved)
}