package p.vasylprokudin.elpassion.extensions

import android.os.Bundle

fun Bundle.getStringNotNull(key: String, defaultValue: String? = null): String = this.getString(key, defaultValue)
    ?: throw IllegalArgumentException("Could not find non-null value for key: $key")

fun Bundle?.getPrintableString(): String {
    this ?: return "null"

    val sb = StringBuilder()
    val keys = this.keySet()
    for (key in keys) {
        sb.append('[').append(key).append('=').append(this.get(key)).append(']')
    }

    return sb.toString()
}
