package com.englizya.datastore.core

import android.content.Context
import com.englizya.datastore.utils.Value.NULL_INT
import com.englizya.datastore.utils.SourceConstants
import com.englizya.datastore.utils.TicketConstants.TICKET_CATEGORY

class TicketDataStore(context: Context) {


    private val ticketSharedPreferences =
        context.getSharedPreferences(SourceConstants.TICKET_SHARED_PREFERENCES,
            Context.MODE_PRIVATE)

    fun setTicketCategory(ticketCategory: Int) {
        ticketSharedPreferences.edit().putInt(TICKET_CATEGORY, ticketCategory).apply()
    }

    fun getTicketCategory() : Int {
        return ticketSharedPreferences.getInt(TICKET_CATEGORY, NULL_INT)
    }
}