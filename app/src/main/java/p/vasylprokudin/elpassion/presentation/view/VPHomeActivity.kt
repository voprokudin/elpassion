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
import kotlinx.android.synthetic.main.vp_layout_appbar.offlineMode
import p.vasylprokudin.elpassion.Constants
import p.vasylprokudin.elpassion.R
import p.vasylprokudin.elpassion.base.VPActivity
import p.vasylprokudin.elpassion.domain.model.VPSearchParams
import p.vasylprokudin.elpassion.extensions.obtainViewModel
import p.vasylprokudin.elpassion.extensions.toGoneVisible
import p.vasylprokudin.elpassion.extensions.toVisibleGone
import p.vasylprokudin.elpassion.presentation.navigation.VPGitHubRepositoriesNavigator
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.DisableView
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.LoadMore
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
        etSearch.setOnEditorActionListener { editText, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                viewModel.clearPreviousSearch()
                val searchQuery = editText.text.toString().trim()
                val searchParams = createSearchParams(searchQuery)
                viewModel.updateSearchParams(searchParams)
                if (viewModel.searchParams.query.isNotEmpty()) maybeFetchRepositories()
            }
            false
        }
    }

    private fun createSearchParams(searchQuery: String) = VPSearchParams(
        query = searchQuery,
        page = Constants.Integer.ONE
    )

    private fun registerConnectivityReceiver() {
        registerReceiver(VPConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun maybeFetchRepositories() {
        progressBarVisibility(visible = true)
        progressBarMoreVisibility(visible = true)
        viewModel.maybeFetchRepositories()
    }

    private fun setUpViewAvailability(enabled: Boolean) {
        enabled.also {
            offlineMode.visibility = it.toGoneVisible()
            searchView.isEnabled = it
        }
        setUpEmptyViewAvailability(enabled)
    }

    private fun setUpEmptyViewAvailability(enabled: Boolean) {
        if (!navigator.isAnyFragmentVisible()) emptyView.visibility = enabled.toGoneVisible()
    }

    private fun disableView() {
        progressBarVisibility(visible = false)
        progressBarMoreVisibility(visible = false)
    }

    private fun maybeShowRepositoriesListFragment(isEmptyList: Boolean) {
        emptyView.visibility = isEmptyList.toVisibleGone()
        progressBarVisibility(visible = false)
        progressBarMoreVisibility(visible = false)
        if (!isEmptyList) showRepositoriesListFragment()
    }

    private fun showRepositoriesListFragment() {
        maybeClearBackStack()
        navigator.showGitHubRepositoriesListFragment()
    }

    private fun maybeClearBackStack() {
        if (navigator.isFragmentAlreadyShown<VPGitHubRepositoriesDetailFragment>()) navigator.clearBackStack()
    }

    private fun showError(errorMessage: String?) {
        progressBarVisibility(visible = false)
        progressBarMoreVisibility(visible = false)
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.vp_cant_download_dialog_title)
            .setMessage(getString(R.string.vp_cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.vp_cant_download_dialog_btn_positive) { _, _ -> maybeFetchRepositories() }
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
                LoadMore -> maybeFetchRepositories()
                is MaybeShowRepositoriesListFragment -> maybeShowRepositoriesListFragment(screenAction.isEmptyList)
                is ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }
}
