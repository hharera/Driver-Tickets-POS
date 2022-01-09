package com.englizya.repository.impl

import android.util.Log
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ManifestoRepositoryImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var manifestoRepositoryImpl: ManifestoRepositoryImpl

    private val TAG = "ManifestoRepositoryImpl"

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getManifesto() = runBlocking {
        manifestoRepositoryImpl
            .getManifesto()
            .onSuccess {
                Log.d(TAG, "getManifesto: $it")
            }
    }
}