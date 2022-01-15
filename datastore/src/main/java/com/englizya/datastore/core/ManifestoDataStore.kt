package com.englizya.datastore.core

import android.content.Context
import com.englizya.datastore.utils.Value.NULL_INT
import com.englizya.datastore.utils.Value.NULL_STRING
import com.englizya.datastore.utils.ManifestoConstants.MANIFESTO_DATE
import com.englizya.datastore.utils.ManifestoConstants.MANIFESTO_NO
import com.englizya.datastore.utils.ManifestoConstants.MANIFESTO_YEAR
import com.englizya.datastore.utils.SourceConstants

class ManifestoDataStore(context: Context) {

    private val manifestoPreferences =
        context.getSharedPreferences(
            SourceConstants.MANIFESTO_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )

    fun setManifestoNo(manifestoNo: Int) {
        manifestoPreferences.edit().putInt(MANIFESTO_NO, manifestoNo).apply()
    }

    fun getManifestoNo(): Int = manifestoPreferences.getInt(MANIFESTO_NO, NULL_INT)

    fun setManifestoYear(year: Int) {
        manifestoPreferences.edit().putInt(MANIFESTO_YEAR, year).apply()
    }

    fun getManifestoYear(): Int = manifestoPreferences.getInt(MANIFESTO_YEAR, NULL_INT)

    fun setManifestoDate(date: String) {
        manifestoPreferences.edit().putString(MANIFESTO_DATE, date).apply()
    }

    fun getManifestoDate(): String = manifestoPreferences.getString(MANIFESTO_DATE, NULL_STRING)!!
}