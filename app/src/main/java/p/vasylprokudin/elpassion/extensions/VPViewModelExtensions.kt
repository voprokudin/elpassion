package p.vasylprokudin.elpassion.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

@Suppress("unused")
inline fun <reified T : ViewModel> Fragment.obtainViewModel(
    viewModelFactory: ViewModelProvider.Factory
): T = ViewModelProviders.of(this, viewModelFactory).get(T::class.java)

inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel(
    viewModelFactory: ViewModelProvider.Factory
): T = ViewModelProviders.of(this, viewModelFactory).get(T::class.java)