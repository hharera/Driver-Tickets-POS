package com.englizya.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.englizya.model.UnPrintedTicket
import com.englizya.model.response.ManifestoDetails

@Dao
interface ManifestoDao {

    @Insert(onConflict = REPLACE)
    fun insertTicket(manifestoDetails: ManifestoDetails)

    @Query(value = "SELECT * from ManifestoDetails where manifestoId = :manifestoId and year = :year limit 1")
    fun getManifesto(manifestoId: Int, year: Int): ManifestoDetails
}