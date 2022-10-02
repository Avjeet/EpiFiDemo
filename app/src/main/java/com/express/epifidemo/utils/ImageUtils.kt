package com.express.epifidemo.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation

object ImageUtils {
    fun setRoundedImage(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .into(imageView)
    }

    fun setRoundedBlurImage(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .load(url)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .transform(
                        CenterCrop(),
                        BlurTransformation(15, 2),
                        GranularRoundedCorners(35f, 35f, 0f, 0f)
                    )
            )
            .into(imageView)
    }

}