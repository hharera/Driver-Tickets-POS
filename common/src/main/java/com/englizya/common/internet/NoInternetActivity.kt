package com.englizya.common.internet

import android.os.Bundle
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.network.ConnectionLiveData
import com.englizya.common.R
import org.koin.android.ext.android.inject

class NoInternetActivity : BaseActivity() {

    private val connectionLiveData: ConnectionLiveData by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_internet)

        connectionLiveData.observe(this) {
            if (it) {
                finish()
            }
        }
    }

}