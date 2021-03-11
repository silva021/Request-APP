package com.silva021.myapplication.DAO

import androidx.room.Dao
import androidx.room.Insert
import com.silva021.myapplication.model.RequestSaved

@Dao
interface RequestSavedDAO {

    @Insert
    fun insertRequest(request: RequestSaved)
}