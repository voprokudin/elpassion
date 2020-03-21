package p.vasylprokudin.elpassion.extensions

import androidx.fragment.app.Fragment

@JvmOverloads
inline fun <reified T : Any> Fragment.argNotNull(key: String, default: T? = null): Lazy<T> = lazy {
    val value = arguments?.get(key)
    requireNotNull(if (value is T) value else default) { "Value for key '$key' was null. Key present: '${arguments?.containsKey(key)}'. Arguments: ${arguments.getPrintableString()}" }
}