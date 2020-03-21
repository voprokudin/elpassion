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

    fun showProgressBar() {
        getVPActivity().showProgressBar()
    }

    fun hideProgressBar() {
        getVPActivity().hideProgressBar()
    }

    fun setToolbarNavigationIcon(@DrawableRes iconResId: Int?) {
        getVPActivity().setToolbarNavigationIcon(iconResId)
    }

    fun setToolbarTitle(@StringRes titleId: Int) {
        getVPActivity().setToolbarTitle(titleId)
    }

    private fun getVPActivity(): VPActivity = (activity as VPActivity)
}