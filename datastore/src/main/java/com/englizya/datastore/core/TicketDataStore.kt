package com.englizya.datastore.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.englizya.datastore.utils.PreferencesName
import com.englizya.datastore.utils.SourceConstants
import com.englizya.datastore.utils.TicketConstants.TICKET_CATEGORIES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TicketDataStore(context: Context) {


    private val ticketDataStore : DataStore<Preferences> = context.ticketDataStore

    suspend fun setTicketCategories(ticketCategories: Set<String>) {
        val ticketCategoriesPreferences = stringSetPreferencesKey(TICKET_CATEGORIES)
        ticketDataStore.edit {
            it[ticketCategoriesPreferences] = ticketCategories
        }
    }

    fun getTicketCategories() : Flow<Set<String>?> {
        val ticketCategoriesPreferences = stringSetPreferencesKey(TICKET_CATEGORIES)
        return ticketDataStore.data.map {
            it[ticketCategoriesPreferences]
        }
    }
}

val Context.ticketDataStore : DataStore<Preferences> by preferencesDataStore(name = PreferencesName.TICKET)