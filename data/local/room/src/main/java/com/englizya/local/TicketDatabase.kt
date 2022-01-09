package com.englizya.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.englizya.model.request.Ticket

@Database(
    version = 1,
    entities = [Ticket::class],
    exportSchema = true,
)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun getMarketDao(): TicketDao
}