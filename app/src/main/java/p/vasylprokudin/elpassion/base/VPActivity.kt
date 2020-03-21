package p.vasylprokudin.elpassion.base

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.vp_toolbar.toolbar
import kotlinx.android.synthetic.main.vp_toolbar.toolbarProgressBar
import p.vasylprokudin.elpassion.R

abstract class VPActivity : DaggerAppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initViews()
    }

    private fun initViews() {
        initActionBar()
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        setToolbarNavigationIcon(R.drawable.ic_back)
        toolbar?.setNavigationOnClickListener { onToolbarNavigateUp() }
        configureActionBar(supportActionBar)
        hideProgressBar()
    }

    private fun configureActionBar(actionBar: ActionBar?) {
        actionBar?.setDisplayShowTitleEnabled(true)
    }

    private fun onToolbarNavigateUp() {
        onBackPressed()
    }

    fun showProgressBar() {
        toolbarProgressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        toolbarProgressBar.visibility = View.GONE
    }

    fun setToolbarNavigationIcon(@DrawableRes iconResId: Int?) {
        if (iconResId == null) toolbar?.navigationIcon = iconResId else toolbar?.setNavigationIcon(iconResId)
    }

    fun setToolbarTitle(@StringRes titleId: Int) {
        supportActionBar?.setTitle(titleId)
    }
}