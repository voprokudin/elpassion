package p.vasylprokudin.elpassion.base

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.vp_home_content.progressBarLoadMore
import kotlinx.android.synthetic.main.vp_toolbar.toolbar
import kotlinx.android.synthetic.main.vp_toolbar.toolbarProgressBar

abstract class VPActivity : DaggerAppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initViews()
    }

    fun setOnToolbarNavigateUp(enabled: Boolean) {
        if (enabled) toolbar?.setNavigationOnClickListener { onToolbarNavigateUp() }
    }

    fun progressBarVisibility(visible: Boolean) {
        toolbarProgressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun progressBarMoreVisibility(visible: Boolean) {
        progressBarLoadMore.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setToolbarNavigationIcon(@DrawableRes iconResId: Int?) {
        if (iconResId == null) toolbar?.navigationIcon = iconResId else toolbar?.setNavigationIcon(iconResId)
    }

    fun setToolbarTitle(@StringRes titleId: Int) {
        supportActionBar?.setTitle(titleId)
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun initViews() {
        initActionBar()
    }

    private fun initActionBar() {
        setSupportActionBar(toolbar)
        setToolbarNavigationIcon(null)
        setOnToolbarNavigateUp(enabled = false)
        configureActionBar(supportActionBar)
        progressBarVisibility(visible = false)
    }

    private fun configureActionBar(actionBar: ActionBar?) {
        actionBar?.setDisplayShowTitleEnabled(true)
    }

    private fun onToolbarNavigateUp() {
        onBackPressed()
    }
}
