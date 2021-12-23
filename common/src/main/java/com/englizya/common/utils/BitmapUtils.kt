package com.englizya.common.utils

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.opensooq.supernova.gligar.GligarPicker
import java.io.ByteArrayOutputStream

class BitmapUtils {

    companion object {

        fun convertImagePathToBitmap(data: Intent): Bitmap? {
            val imagesList = data.extras?.getStringArray(GligarPicker.IMAGES_RESULT)
            if (!imagesList.isNullOrEmpty()) {
                BitmapFactory.decodeFile(imagesList[0])?.let {
                    return it
                }
            }
            return null
        }

        fun convertImageBitmapToByteArray(imageBitmap: Bitmap): ByteArray {
            val inputStream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, inputStream)
            return inputStream.toByteArray()
        }
    }
}