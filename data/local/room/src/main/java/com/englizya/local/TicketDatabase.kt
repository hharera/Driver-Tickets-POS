package com.englizya.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.englizya.model.UnPrintedTicket
import com.englizya.model.request.Ticket

@Database(
    version = 1,
    entities = [Ticket::class, UnPrintedTicket::class],
    exportSchema = true,
)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun getMarketDao(): TicketDao
}