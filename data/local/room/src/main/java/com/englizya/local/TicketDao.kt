package com.englizya.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.englizya.model.request.Ticket

@Dao
interface TicketDao {

    @Insert
    fun insertTicket(ticket: Ticket)

    @Insert
    fun insertTickets(tickets: List<Ticket>)

    @Query(value = "SELECT * from Ticket")
    fun getTickets(): List<Ticket>
}