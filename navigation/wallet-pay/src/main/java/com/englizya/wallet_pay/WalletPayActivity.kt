package com.englizya.wallet_pay

import android.os.Bundle
import com.englizya.common.base.BaseActivity
import com.englizya.wallet_pay.databinding.ActivityWalletPayBinding

class WalletPayActivity : BaseActivity() {

    private lateinit var binding: ActivityWalletPayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}