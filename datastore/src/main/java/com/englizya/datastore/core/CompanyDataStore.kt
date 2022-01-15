package com.englizya.datastore.core

import android.content.Context
import com.englizya.datastore.utils.Value.CUSTOMER_SUPPORT
import com.englizya.datastore.utils.SourceConstants

class CompanyDataStore(context: Context) {

    private val companySharedPreferences =
        context.getSharedPreferences(SourceConstants.COMPANY_SHARED_PREFERENCES,
            Context.MODE_PRIVATE)


    fun getCustomerSupportNumber() : String{
        return CUSTOMER_SUPPORT
    }
}