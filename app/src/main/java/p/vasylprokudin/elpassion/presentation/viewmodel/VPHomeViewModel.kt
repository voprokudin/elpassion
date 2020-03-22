package p.vasylprokudin.elpassion.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import p.vasylprokudin.elpassion.data.model.VPRawRepositories
import p.vasylprokudin.elpassion.domain.interactor.VPGetRepositoriesUseCase
import p.vasylprokudin.elpassion.domain.interactor.base.VPEmptySingleObserver
import p.vasylprokudin.elpassion.util.network.VPNetworkStateProvider
import javax.inject.Inject

class VPHomeViewModel
@Inject constructor(
    private val networkStateProvider: VPNetworkStateProvider,
    private val getRepositoriesUseCase: VPGetRepositoriesUseCase
) : ViewModel() {

    val screenState: LiveData<ScreenState> by lazy { mutableScreenState }

    private val mutableScreenState = MutableLiveData<ScreenState>()

    fun maybeFetchRepositories() {
        if (networkStateProvider.isNetworkConnected()) fetchRepositories() else mutableScreenState.value = ScreenState.DisableView
    }

    private fun fetchRepositories() {
        getRepositoriesUseCase.execute(observer = GetGitHubRepositoriesObserver())
    }

    internal inner class GetGitHubRepositoriesObserver : VPEmptySingleObserver<VPRawRepositories>() {
        override fun onSuccess(result: VPRawRepositories) {
            mutableScreenState.value = ScreenState.ShowRepositoriesListFragment
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ScreenState.ShowGeneralError(throwable.message)
        }
    }

    sealed class ScreenState {
        object ShowRepositoriesListFragment : ScreenState()
        object DisableView : ScreenState()
        class ShowGeneralError(val errorMessage: String?) : ScreenState()
    }
}