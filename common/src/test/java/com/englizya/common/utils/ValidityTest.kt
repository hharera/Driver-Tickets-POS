package com.englizya.common.utils

import junit.framework.TestCase
import org.junit.Test

class ValidityTest : TestCase() {

    @Test
    fun checkPhoneNumberValidity() {
        assertEquals(Validity.checkPhoneNumber("01062227714"), true )
    }

    @Test
    fun checkEmailValidity() {
        assertEquals(Validity.checkEmail("hassanstar201118@gmail.com"), true )
    }
}