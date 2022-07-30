package com.englizya.printer

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.englizya.model.response.ShiftReportResponse
import com.englizya.printer.di.printerModule
import com.englizya.sunmiprinter.di.sunmiPrinterModule
import org.joda.time.DateTime
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject


@RunWith(AndroidJUnit4::class)
class ReportPrinterTest : KoinTest {


    private val modules = listOf(sunmiPrinterModule, printerModule)
    private val ticketPrinter: TicketPrinter by inject()

    @Before
    fun setUp() {
        loadKoinModules(modules)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun test_to_print_end_shift_report() {
        ticketPrinter.printShiftReport(
            ShiftReportResponse(
                "Dummy",
                125,
                "Dummy",
                "Dummy",
                DateTime.now().toString("yyyy-MM-dd hh:mm:ss"),
                125,
                125,
                125,
                125,
                DateTime.now().toString("yyyy-MM-dd hh:mm:ss"),
                DateTime.now().toString("yyyy.MM.dd hh:mm:ss"),
                125,
                125,
            )
        )
    }
}