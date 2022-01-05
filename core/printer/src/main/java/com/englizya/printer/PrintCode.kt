package com.englizya.printer

import android.graphics.BitmapFactory

class PrintCode {

    fun print() {

        Thread {
            PaxPrinter
                .getInstance().init()
            PaxPrinter
                .getInstance()
                .fontSet(
                    asciiSpinner.getSelectedItem() as EFontTypeAscii,
                    extCodeSpinner.getSelectedItem() as EFontTypeExtCode
                )
            val options = BitmapFactory.Options()
            options.inScaled = false
            val bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rect, options)
            //                        Log.i("Test", "width:"+bitmap.getWidth()+"height:"+bitmap.getHeight());
            PaxPrinter.getInstance().printBitmap(bitmap)
            PaxPrinter.getInstance().spaceSet(
                wordSpaceEt.getText().toString().toByte(),
                lineSpaceEt.getText().toString().toByte()
            )
            PaxPrinter.getInstance().leftIndents(leftIndentEt.getText().toString().toShort())
            PaxPrinter.getInstance().setGray(setGrayEt.getText().toString().toInt())
            if (doubleWidth) {
                PaxPrinter.getInstance().setDoubleWidth(doubleWidth, doubleWidth)
            }
            if (doubleHeight) {
                PaxPrinter.getInstance().setDoubleHeight(doubleHeight, doubleHeight)
            }
            PaxPrinter.getInstance().setInvert(invert)
            val str: String = printStrEt.getText().toString()
            if (str != null && str.length > 0) PaxPrinter.getInstance().printStr(str, null)
            PaxPrinter.getInstance().step(stepEt.getText().toString().trim { it <= ' ' }.toInt())
            getDotLineTv.post(Runnable {
                getDotLineTv.setText(
                    PaxPrinter.getInstance().getDotLine().toString() + ""
                )
            })
            val status: String = PaxPrinter.getInstance().start()
            statusTv.post(Runnable { statusTv.setText(status) })
        }.start()


    }

}