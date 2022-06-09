package com.englizya.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.englizya.model.UnPrintedTicket
import com.englizya.model.request.Ticket
import com.englizya.model.response.ManifestoDetails

@Database(
    version = 3,
    entities = [Ticket::class, UnPrintedTicket::class ],
    exportSchema = true,
)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun getTicketDao(): TicketDao
}