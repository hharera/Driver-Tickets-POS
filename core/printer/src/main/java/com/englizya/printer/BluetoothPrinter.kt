package com.englizya.printer

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*


class BluetoothPrinter : AppCompatActivity() {
    val PERMISSION_BLUETOOTH = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH),
                PERMISSION_BLUETOOTH
            );
        } else {
            print()
        }
    }

    private fun print() {
        PrinterTester.getInstance().init()

        PrinterTester.getInstance().spaceSet("1".toByte(), "1".toByte())
        PrinterTester.getInstance().leftIndents(leftIndentEt.getText().toString().toShort())
        PrinterTester.getInstance().setGray(4)

        val options = BitmapFactory.Options()
        options.inScaled = false
        PrinterTester.getInstance().setInvert(true)

        val dayOne = DateTime(2022, 1, 1, 0, 0)
        val now = DateTime()

        var bitmap = BitmapFactory.decodeResource(
            resources, R.drawable.ic_pax, options
        )
        PrinterTester.getInstance().printBitmap(bitmap)
        PrinterTester.getInstance().setInvert(false)
        PrinterTester.getInstance().step(30)
        PrinterTester.getInstance()
            .printStr("Serial : 147-781-" + (now.millis - dayOne.millis), null)
        PrinterTester.getInstance().step(20)
        PrinterTester.getInstance().printStr(
            "Date : " + SimpleDateFormat("yyyy.MM.dd").format(
                Date()
            ), null
        )
        PrinterTester.getInstance().step(20)
        PrinterTester.getInstance().printStr("Driver code : 800159", null)
        PrinterTester.getInstance().step(20)
        PrinterTester.getInstance().printStr("Car code : 7000019", null)
        PrinterTester.getInstance().step(20)
        PrinterTester.getInstance().printStr(
            "Time : " + SimpleDateFormat("HH:mm:ss").format(
                Date()
            ), null
        )
        PrinterTester.getInstance().step(20)
        PrinterTester.getInstance().printStr("Line : M4", null)
        PrinterTester.getInstance().step(20)

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.cost_5, options)
        PrinterTester.getInstance().printBitmap(bitmap)
        PrinterTester.getInstance().step(120)
        PrinterTester.getInstance().cutPaper(1)
    }

}