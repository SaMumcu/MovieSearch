package com.samumcu.moviesearch.utils

import android.app.Activity
import android.content.Context
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
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

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}