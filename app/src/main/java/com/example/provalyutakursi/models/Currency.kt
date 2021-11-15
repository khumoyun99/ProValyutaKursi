package com.example.provalyutakursi.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val cb_price: String,
    val code: String,
    val date: String,
    var nbu_buy_price: String,
    var nbu_cell_price: String,
    val title: String,
    var photoUrl:String? = null
):Serializable