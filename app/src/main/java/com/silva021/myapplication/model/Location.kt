package com.silva021.myapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Location {
    @SerializedName("nome")
    @Expose
    val nome: String = ""
    @SerializedName("sigla")
    @Expose
    val sigla: String = ""
}