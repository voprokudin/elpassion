package p.vasylprokudin.elpassion.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import p.vasylprokudin.elpassion.Constants
import p.vasylprokudin.elpassion.data.model.VPRawRepositories.VPRawItem
import p.vasylprokudin.elpassion.domain.interactor.VPGetRepositoriesUseCase
import p.vasylprokudin.elpassion.domain.interactor.base.VPEmptySingleObserver
import p.vasylprokudin.elpassion.domain.model.VPSearchParams
import p.vasylprokudin.elpassion.util.network.VPNetworkStateProvider
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.LoadMore
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.DisableView
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.MaybeShowRepositoriesListFragment
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel.ScreenState.ShowGeneralError
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

    internal var searchParams: VPSearchParams = VPSearchParams(
        query = Constants.Strings.EMPTY,
        page = Constants.Integer.ONE
    )

    private var previousTotal = Constants.Integer.ZERO

    private var pageNumber = Constants.Integer.ONE

    private var isLoading = true

    fun updateSearchParams(searchParams: VPSearchParams) {
        this.searchParams = searchParams
    }

    fun maybeFetchRepositories() {
        if (networkStateProvider.isNetworkConnected()) fetchRepositories() else mutableScreenState.value = DisableView
    }

    fun clearPreviousSearch() {
        mutableRepositoriesInfoList.value = arrayListOf()
        previousTotal = Constants.Integer.ZERO
        pageNumber = Constants.Integer.ONE
        isLoading = true
    }

    fun onScroll(dy: Int, visibleItemCount: Int, totalItemCount: Int, pastVisibleItems: Int) {
        if (dy > 0) {
            if (isLoading) {
                if (totalItemCount > previousTotal) {
                    isLoading = false
                    previousTotal = totalItemCount
                }
            }
            val viewThreshold = searchParams.perPage
            if (!isLoading && (totalItemCount - visibleItemCount) <=
                pastVisibleItems + viewThreshold) {
                pageNumber ++
                searchParams = searchParams.copy(page = pageNumber)
                mutableScreenState.value = LoadMore
                isLoading = true
            }
        }
    }

    private fun fetchRepositories() {
        getRepositoriesUseCase.execute(observer = GetGitHubRepositoriesObserver(), params = searchParams)
    }

    internal inner class GetGitHubRepositoriesObserver : VPEmptySingleObserver<ArrayList<VPRawItem>>() {
        override fun onSuccess(result: ArrayList<VPRawItem>) {
            val oldList = mutableRepositoriesInfoList.value
            oldList?.addAll(result)
            val newList = oldList ?: result
            mutableScreenState.value = MaybeShowRepositoriesListFragment(newList.isEmpty())
            mutableRepositoriesInfoList.value = newList
        }

        override fun onError(throwable: Throwable) {
            mutableScreenState.value = ShowGeneralError(throwable.message)
        }
    }

    sealed class ScreenState {
        class MaybeShowRepositoriesListFragment(val isEmptyList: Boolean) : ScreenState()
        object DisableView : ScreenState()
        object LoadMore : ScreenState()
        class ShowGeneralError(val errorMessage: String?) : ScreenState()
    }
}
