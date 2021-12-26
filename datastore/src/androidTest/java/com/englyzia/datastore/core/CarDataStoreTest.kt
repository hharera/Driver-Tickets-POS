package com.englyzia.datastore.core

import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CarDataStoreTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var carDataStore : CarDataStore

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testGetCarCode() {
        carDataStore.setCarCode(123)
        carDataStore.getCarCode().let {
            Truth.assertThat(it).isEqualTo(123)
        }
    }

    fun testGetCarLineCode() {}

    fun testGetCarLineDescription() {}
}