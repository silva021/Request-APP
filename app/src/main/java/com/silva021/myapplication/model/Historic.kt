package com.silva021.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_Historic")
data class Historic (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "URL") val url : String?,
    @ColumnInfo(name = "typeRequest") val typeRequest : String?,
    @ColumnInfo(name = "code") val code : Int?,
    @ColumnInfo(name = "date") val dateRequest : String?
)