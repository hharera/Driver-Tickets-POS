package com.englizya.datastore.core

import android.content.Context
import com.englizya.datastore.base.GeneralConstants.CUSTOMER_SUPPORT
import com.englizya.datastore.base.SourceConstants

class CompanyDataStore(context: Context) {

    private val companySharedPreferences =
        context.getSharedPreferences(SourceConstants.COMPANY_SHARED_PREFERENCES,
            Context.MODE_PRIVATE)


    fun getCustomerSupportNumber() : String{
        return CUSTOMER_SUPPORT
    }
}