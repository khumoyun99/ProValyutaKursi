package com.example.provalyutakursi.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.provalyutakursi.models.Currency
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface CurrencyDao {

    @Insert(onConflict = REPLACE)
    fun addCurrency(currency: Currency):Maybe<Long>


    @Query("select * from currency")
    fun getAllCurrency(): Flowable<List<Currency>>


}