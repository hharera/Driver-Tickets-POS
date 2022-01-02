package com.englizya.datastore.core

import android.content.Context
import com.englizya.datastore.base.GeneralConstants.NULL_INT
import com.englizya.datastore.base.SourceConstants
import com.englizya.datastore.base.TicketConstants.TICKET_CATEGORY

class TicketDataStore(context: Context) {


    private val ticketSharedPreferences =
        context.getSharedPreferences(SourceConstants.TICKET_SHARED_PREFERENCES,
            Context.MODE_PRIVATE)

    fun setTicketCategory(ticketCategory: Int) {
        //TODO differences between commit and apply
        ticketSharedPreferences.edit().putInt(TICKET_CATEGORY, ticketCategory).apply()
    }

    fun getTicketCategory() : Int {
        return ticketSharedPreferences.getInt(TICKET_CATEGORY, NULL_INT)
    }
}