package com.lukabaia.yummy.extensions

import android.content.Context
import android.widget.ImageView

fun ImageView.setTint(context: Context, color: Int) {
    this.setColorFilter(
        context.getColor(color),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
}