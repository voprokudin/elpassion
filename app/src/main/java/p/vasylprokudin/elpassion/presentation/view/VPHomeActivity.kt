package p.vasylprokudin.elpassion.presentation.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import p.vasylprokudin.elpassion.R
import p.vasylprokudin.elpassion.base.VPActivity
import p.vasylprokudin.elpassion.extensions.obtainViewModel
import p.vasylprokudin.elpassion.presentation.navigation.VPGitHubRepositoriesNavigator
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.ShowRepositoriesListFragment
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.ShowGeneralError
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.DisableView
import javax.inject.Inject

class VPHomeActivity : VPActivity() {

    @Inject
    lateinit var navigator: VPGitHubRepositoriesNavigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { obtainViewModel<VPHomeViewModel>(viewModelFactory) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vp_activity_home)
        setUpView()
        setupViewModel()
        maybeFetchRepositories()
    }

    private fun maybeFetchRepositories() {
        showProgressBar()
        viewModel.maybeFetchRepositories()
    }

    private fun setUpView() {

    }

    private fun disableView() {

    }

    private fun showRepositoriesListFragment() {
        hideProgressBar()
        navigator.showGitHubRepositoriesListFragment()
    }

    private fun showError(errorMessage: String?) {
        hideProgressBar()
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
                ShowRepositoriesListFragment -> showRepositoriesListFragment()
                is ShowGeneralError -> showError(screenAction.errorMessage)
            }
        }
    }
}
