package com.inventiv.multipaysdk.util

import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.inventiv.multipaysdk.R

fun Fragment.toolbar() = requireActivity().findViewById<Toolbar>(R.id.toolbar)!!

fun Fragment.title(strId: Int) {
    toolbar().title = context?.getString(strId)
}

fun Fragment.toolbarBackground(resId: Int) {
    val drawable = ContextCompat.getDrawable(requireContext(), resId)
    toolbar().background = drawable
}

fun Fragment.toolbarColor(colorId: Int) {
    toolbar().setBackgroundColor(colorId)
}

fun Fragment.toolbarClose() {
    toolbarIcon(R.drawable.ic_nav_close)
}

fun Fragment.toolbarBack() {
    // val typedValueAttr = TypedValue()
    // context?.theme?.resolveAttribute(android.R.attr.homeAsUpIndicator, typedValueAttr, true)
    // toolbarIcon(typedValueAttr.resourceId)
    toolbarIcon(R.drawable.ic_nav_back)
}

fun Fragment.toolbarIcon(drawableId: Int) {
    val drawable = ContextCompat.getDrawable(requireContext(), drawableId)
    toolbar().navigationIcon = drawable
}