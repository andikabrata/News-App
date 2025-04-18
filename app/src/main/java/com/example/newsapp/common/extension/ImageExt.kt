package com.example.newsapp.common.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.io.File
import java.io.IOException

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .into(this)
}

fun ImageView.loadImageCard(url: String) {
    Glide.with(this.context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .into(this)
}

fun ImageView.setPic(currentPhotoPath: String) {
    // Get the dimensions of the View
    val targetW: Int = this.width
    val targetH: Int = this.height

    val bmOptions = BitmapFactory.Options().apply {
        // Get the dimensions of the bitmap
        inJustDecodeBounds = true

        val photoW: Int = outWidth
        val photoH: Int = outHeight

        // Determine how much to scale down the image
        val scaleFactor: Int = (photoW / targetW).coerceAtMost(photoH / targetH)

        // Decode the image file into a Bitmap sized to fill the View
        inJustDecodeBounds = false
        inSampleSize = scaleFactor
        inPurgeable = true
    }

    BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
        val selectedImage = Uri.fromFile(File(currentPhotoPath))
        try {
            this.setImageBitmap(rotateImageIfRequired(context, bitmap, selectedImage))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

private fun rotateImageIfRequired(context: Context, img: Bitmap, selectedImage: Uri): Bitmap {
    val input = context.contentResolver.openInputStream(selectedImage)
    val ei: ExifInterface =
        if (Build.VERSION.SDK_INT > 23) ExifInterface(input!!) else ExifInterface(
            selectedImage.path!!
        )
    return when (ei.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
        else -> img
    }
}

private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degree.toFloat())
    val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
    img.recycle()
    return rotatedImg
}
    