package com.englizya.repository

import com.englizya.api.WalletService
import com.englizya.model.response.WalletDetails
import javax.inject.Inject

interface WalletRepository {
    suspend fun getWallet(driverToken : String, uid: String): Result<WalletDetails>
}

class WalletRepositoryImpl @Inject constructor(
    private val walletService: WalletService
) : WalletRepository {

    override suspend fun getWallet(driverToken: String, uid: String): Result<WalletDetails> = kotlin.runCatching {
        walletService.getWalletDetails(driverToken, uid)
    }
}