package com.englizya.printer

import android.graphics.BitmapFactory
import com.englizya.model.request.Ticket
import com.pax.dal.entity.EFontTypeAscii
import com.pax.dal.entity.EFontTypeExtCode
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class TicketPrinterViewModel @Inject constructor(
    private val paxPrinter: PaxPrinter
) {

    fun printTicket(ticket : Ticket) {
        paxPrinter.fontSet(
            asciiSpinner.getSelectedItem() as EFontTypeAscii,
            extCodeSpinner.getSelectedItem() as EFontTypeExtCode
        )

        paxPrinter.spaceSet("1".toByte(), "1".toByte())
        paxPrinter.leftIndents(leftIndentEt.getText().toString().toShort())
        paxPrinter.setGray(setGrayEt.getText().toString().toInt())

        val options = BitmapFactory.Options()
        options.inScaled = false
        paxPrinter.setDoubleWidth(true, true)
        paxPrinter.setDoubleHeight(true, true)
        paxPrinter.setInvert(true)

        var bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_pax, options)
        paxPrinter.printBitmap(bitmap)
        paxPrinter.setInvert(invert)
        paxPrinter.step(150)
        paxPrinter.printStr("Driver Code : 147_781_" + Date().time, null)
        paxPrinter.step(150)
        paxPrinter.spaceSet("1".toByte(), "1".toByte())
        paxPrinter.step(150)
        paxPrinter.printStr("Date : " + SimpleDateFormat("yyyy.MM.dd").format(
                Date()
            ), null
        )
        paxPrinter.step(150)
        paxPrinter.spaceSet("1".toByte(), "1".toByte())
        paxPrinter.printStr(
            "Time : " + SimpleDateFormat("HH:mm:ss").format(
                Date()
            ), null
        )
        paxPrinter.step(150)
        paxPrinter.spaceSet("1".toByte(), "1".toByte())
        paxPrinter.step(150)

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cost_5, options)
        paxPrinter.printBitmap(bitmap)
        paxPrinter.step(150)

        getDotLineTv.post(Runnable {
            getDotLineTv.setText(
                paxPrinter.getDotLine().toString() + ""
            )
        })
        val status: String = paxPrinter.start()
        statusTv.post(Runnable { statusTv.setText(status) })


    }

}