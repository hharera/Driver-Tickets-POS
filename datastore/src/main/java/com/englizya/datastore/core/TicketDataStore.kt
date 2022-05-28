package com.englizya.datastore.core

import android.content.Context
import com.englizya.datastore.utils.SourceConstants
import com.englizya.datastore.utils.TicketConstants.TICKET_CATEGORIES

class TicketDataStore(context: Context) {


    private val ticketSharedPreferences =
        context.getSharedPreferences(SourceConstants.TICKET_SHARED_PREFERENCES,
            Context.MODE_PRIVATE)

    fun setTicketCategories(ticketCategories: Set<String>) {
        ticketSharedPreferences.edit().putStringSet(TICKET_CATEGORIES, ticketCategories).apply()
    }

    fun getTicketCategories() : Set<String>? {
        return ticketSharedPreferences.getStringSet(TICKET_CATEGORIES, HashSet())
    }
}