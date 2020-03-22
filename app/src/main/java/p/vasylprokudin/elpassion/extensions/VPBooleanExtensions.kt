package p.vasylprokudin.elpassion.extensions

import android.view.View

fun Boolean.toVisibleGone() = if (this) View.VISIBLE else View.GONE

fun Boolean.toGoneVisible() = if (this) View.GONE else View.VISIBLE