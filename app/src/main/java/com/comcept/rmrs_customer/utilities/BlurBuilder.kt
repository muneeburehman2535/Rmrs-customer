package com.comcept.rmrs_customer.utilities

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur


class BlurBuilder {



    companion object{
        private val BITMAP_SCALE = 0.8f
        private val BLUR_RADIUS = 10.5f
        fun blur(context: Context?, image: Bitmap): Bitmap? {
            val width = Math.round(image.width * BITMAP_SCALE)
            val height = Math.round(image.height * BITMAP_SCALE)
            val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
            val outputBitmap = Bitmap.createBitmap(inputBitmap)
            val rs: RenderScript = RenderScript.create(context)
            val theIntrinsic: ScriptIntrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
            val tmpIn: Allocation = Allocation.createFromBitmap(rs, inputBitmap)
            val tmpOut: Allocation = Allocation.createFromBitmap(rs, outputBitmap)
            theIntrinsic.setRadius(BLUR_RADIUS)
            theIntrinsic.setInput(tmpIn)
            theIntrinsic.forEach(tmpOut)
            tmpOut.copyTo(outputBitmap)
            return outputBitmap
        }
    }


}