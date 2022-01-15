package com.englizya.common.internet

import android.os.Bundle
import com.englizya.common.base.BaseActivity
import com.englizya.common.utils.network.ConnectionLiveData
import com.englizya.common.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoInternetActivity : BaseActivity() {

    @Inject
    lateinit var connectionLiveData: ConnectionLiveData

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