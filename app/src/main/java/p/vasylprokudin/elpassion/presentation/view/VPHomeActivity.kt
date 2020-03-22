package p.vasylprokudin.elpassion.presentation.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.vp_activity_home.emptyView
import kotlinx.android.synthetic.main.vp_activity_home.searchView
import p.vasylprokudin.elpassion.R
import p.vasylprokudin.elpassion.base.VPActivity
import p.vasylprokudin.elpassion.data.model.VPRawRepositories
import p.vasylprokudin.elpassion.util.network.VPConnectivityReceiver.VPConnectivityReceiverListener
import p.vasylprokudin.elpassion.extensions.obtainViewModel
import p.vasylprokudin.elpassion.extensions.toGoneVisible
import p.vasylprokudin.elpassion.extensions.toVisibleGone
import p.vasylprokudin.elpassion.presentation.navigation.VPGitHubRepositoriesNavigator
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.MaybeShowRepositoriesListFragment
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.ShowGeneralError
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.DisableView
import p.vasylprokudin.elpassion.util.network.VPConnectivityReceiver
import javax.inject.Inject

class VPHomeActivity : VPActivity(), VPConnectivityReceiverListener {

    @Inject
    lateinit var navigator: VPGitHubRepositoriesNavigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { obtainViewModel<VPHomeViewModel>(viewModelFactory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vp_activity_home)
        setUpView()
        setUpViewAvailability(enabled = true)
        setupViewModel()
        maybeFetchRepositories()
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        setUpViewAvailability(enabled = isConnected)
    }

    override fun onResume() {
        super.onResume()
        VPConnectivityReceiver.connectivityReceiverListener = this
    }

    private fun setUpView() {
        registerConnectivityReceiver()
    }

    private fun registerConnectivityReceiver() {
        registerReceiver(VPConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun maybeFetchRepositories() {
        progressBarVisibility(visible = true)
        viewModel.maybeFetchRepositories()
    }

    private fun setUpViewAvailability(enabled: Boolean) {
        enabled.let {
            searchView.isEnabled = it
            emptyView.visibility = it.toGoneVisible()
        }
    }

    private fun disableView() {
        progressBarVisibility(visible = false)
    }

    private fun maybeShowRepositoriesListFragment(result: VPRawRepositories) {
        val repositories = result.items
        emptyView.visibility = if (repositories.isEmpty()) View.VISIBLE else View.GONE
        progressBarVisibility(visible = false)
        if (repositories.isNotEmpty()) showRepositoriesListFragment(result)
    }

    private fun showRepositoriesListFragment(result: VPRawRepositories) {
        navigator.showGitHubRepositoriesListFragment()
    }

    private fun showError(errorMessage: String?) {
        progressBarVisibility(visible = false)
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.vp_cant_download_dialog_title)
            .setMessage(getString(R.string.vp_cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.vp_cant_download_dialog_btn_positive) { _, _ ->  maybeFetchRepositories()}
            .setNegativeButton(R.string.vp_cant_download_dialog_btn_negative) { _, _ -> finish() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private fun setupViewModel() {
        viewModel.screenState.observe(this, ScreenActionObserver())
    }

    private inner class ScreenActionObserver : Observer<VPHomeViewModel.ScreenState> {
        override fun onChanged(screenAction: VPHomeViewModel.ScreenState?) {
            screenAction ?: return

            when (screenAction) {
                DisableView -> disableView()
                is MaybeShowRepositoriesListFragment -> maybeShowRepositoriesListFragment(screenAction.result)
                is ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }
}
