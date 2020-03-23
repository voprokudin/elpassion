package p.vasylprokudin.elpassion.presentation.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.layout_search.etSearch
import kotlinx.android.synthetic.main.vp_home_content.searchView
import kotlinx.android.synthetic.main.vp_home_content.emptyView
import p.vasylprokudin.elpassion.R
import p.vasylprokudin.elpassion.base.VPActivity
import p.vasylprokudin.elpassion.data.model.VPRawRepositories
import p.vasylprokudin.elpassion.extensions.obtainViewModel
import p.vasylprokudin.elpassion.extensions.toGoneVisible
import p.vasylprokudin.elpassion.extensions.toVisibleGone
import p.vasylprokudin.elpassion.presentation.navigation.VPGitHubRepositoriesNavigator
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.DisableView
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.MaybeShowRepositoriesListFragment
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.ShowGeneralError
import p.vasylprokudin.elpassion.util.network.VPConnectivityReceiver
import p.vasylprokudin.elpassion.util.network.VPConnectivityReceiver.VPConnectivityReceiverListener
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
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        setUpViewAvailability(enabled = isConnected)
    }

    override fun onResume() {
        super.onResume()
        VPConnectivityReceiver.connectivityReceiverListener = this
    }

    private fun setUpView() {
        setToolbarTitle(R.string.vp_toolbar_search)
        registerConnectivityReceiver()
        setUpSearchView()
    }

    private fun setUpSearchView() {
        etSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val searchQuery = v.text.toString().trim()
                if (searchQuery.isNotEmpty()) maybeFetchRepositories(searchQuery)
            }
            false
        }
    }

    private fun registerConnectivityReceiver() {
        registerReceiver(VPConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun maybeFetchRepositories(query: String) {
        progressBarVisibility(visible = true)
        viewModel.maybeFetchRepositories(query)
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
        emptyView.visibility = repositories.isEmpty().toVisibleGone()
        progressBarVisibility(visible = false)
        if (repositories.isNotEmpty()) showRepositoriesListFragment()
    }

    private fun showRepositoriesListFragment() {
        navigator.showGitHubRepositoriesListFragment()
    }

    private fun showError(errorMessage: String?) {
        progressBarVisibility(visible = false)
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.vp_cant_download_dialog_title)
            .setMessage(getString(R.string.vp_cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.vp_cant_download_dialog_btn_positive) { _, _ ->  maybeFetchRepositories(etSearch.text.toString())}
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
