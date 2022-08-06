package com.englizya.printer

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.englizya.printer.di.printerModule
import com.englizya.sunmiprinter.di.sunmiPrinterModule
import io.philippeboisney.common_test.data.longTicket
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject


@RunWith(AndroidJUnit4::class)
class LongTicketPrinterTest : KoinTest {


    private val modules = listOf(sunmiPrinterModule, printerModule)
    private val ticketPrinterImpl: TicketPrinter by inject()

    @Before
    fun setUp() {
        loadKoinModules(modules)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun test_to_print_long_ticket() {
        ticketPrinterImpl.printTicket(
            longTicket
        )
    }
}