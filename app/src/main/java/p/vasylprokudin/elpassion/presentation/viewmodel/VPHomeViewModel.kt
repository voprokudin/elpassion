package p.vasylprokudin.elpassion.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import p.vasylprokudin.elpassion.data.model.VPRawRepositories.VPRawItem
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

    val repositoriesInfoList: LiveData<ArrayList<VPRawItem>> by lazy { mutableRepositoriesInfoList }

    private val mutableRepositoriesInfoList = MutableLiveData<ArrayList<VPRawItem>>()

    fun maybeFetchRepositories(query: String) {
        if (networkStateProvider.isNetworkConnected()) fetchRepositories(query) else mutableScreenState.value = ScreenState.DisableView
    }

    private fun fetchRepositories(query: String) {
        getRepositoriesUseCase.execute(observer = GetGitHubRepositoriesObserver(), params = query)
    }

    internal inner class GetGitHubRepositoriesObserver : VPEmptySingleObserver<ArrayList<VPRawItem>>() {
        override fun onSuccess(result: ArrayList<VPRawItem>) {
            println(result.size)
            mutableRepositoriesInfoList.value = result
            mutableScreenState.value = ScreenState.MaybeShowRepositoriesListFragment(result)
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ScreenState.ShowGeneralError(throwable.message)
        }
    }

    sealed class ScreenState {
        class MaybeShowRepositoriesListFragment(val result: ArrayList<VPRawItem>) : ScreenState()
        object DisableView : ScreenState()
        class ShowGeneralError(val errorMessage: String?) : ScreenState()
    }
}
