package com.samumcu.moviesearch.utils

import android.text.Spanned
import android.widget.ImageView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.samumcu.moviesearch.R

object UIUtils {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadImageWithGlide(path: String?) {
        Glide.with(this)
            .load(path)
            .placeholder(R.color.transparent_gray)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }

    @JvmStatic
    fun formatRate(text: String?): Spanned {
        return HtmlCompat.fromHtml(
            "<b><font color='#000000'>" + text + "</font></b>" + "/10",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }
}