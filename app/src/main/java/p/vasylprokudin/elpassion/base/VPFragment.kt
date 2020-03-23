package p.vasylprokudin.elpassion.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dagger.android.support.DaggerFragment

abstract class VPFragment : DaggerFragment() {

    protected abstract val getLayoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(getLayoutResId, container, false)

    fun progressBarVisibility(visible: Boolean) {
        vpActivity().progressBarVisibility(visible)
    }

    fun setToolbarNavigationIcon(@DrawableRes iconResId: Int?) {
        vpActivity().setToolbarNavigationIcon(iconResId)
    }

    fun setOnToolbarNavigateUp(enabled: Boolean) {
        vpActivity().setOnToolbarNavigateUp(enabled)
    }

    fun setToolbarTitle(@StringRes titleId: Int) {
        vpActivity().setToolbarTitle(titleId)
    }

    fun setToolbarTitle(title: String) {
        vpActivity().setToolbarTitle(title)
    }

    fun vpActivity(): VPActivity = (activity as VPActivity)
}