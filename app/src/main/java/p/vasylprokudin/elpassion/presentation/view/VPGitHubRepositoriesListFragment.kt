package p.vasylprokudin.elpassion.presentation.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.vp_fragment_github_repositories_list.rvRepositories
import p.vasylprokudin.elpassion.R
import p.vasylprokudin.elpassion.base.VPFragment
import p.vasylprokudin.elpassion.data.model.VPRawRepositories.VPRawItem
import p.vasylprokudin.elpassion.extensions.obtainViewModel
import p.vasylprokudin.elpassion.presentation.navigation.VPGitHubRepositoriesNavigator
import p.vasylprokudin.elpassion.presentation.view.list.adapter.VPRepositoriesAdapter
import p.vasylprokudin.elpassion.presentation.view.list.listener.VPRepositoryClickedListener
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel
import javax.inject.Inject

class VPGitHubRepositoriesListFragment :
    VPFragment(),
    VPRepositoryClickedListener {

    companion object {
        @JvmStatic
        fun newInstance(): VPGitHubRepositoriesListFragment = VPGitHubRepositoriesListFragment()
    }

    @Inject
    lateinit var repositoryAdapter: VPRepositoriesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: VPGitHubRepositoriesNavigator

    private val activityViewModel by lazy { vpActivity().obtainViewModel<VPHomeViewModel>(viewModelFactory) }

    private var pastVisibleItems = 0

    private var visibleItemCount = 0

    private var totalItemCount = 0

    override val getLayoutResId: Int = R.layout.vp_fragment_github_repositories_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpViewModel()
        setUpView()
    }

    override fun onRepositoryRowClicked(repository: VPRawItem) {
        navigator.showGitHubRepositoriesDetailsFragment(repository)
    }

    private fun setUpViewModel() {
        activityViewModel.repositoriesInfoList.observe(this, Observer { updateRepositoryAdapterBySearchResults(it) })
    }

    private fun updateRepositoryAdapterBySearchResults(result: ArrayList<VPRawItem>) {
        repositoryAdapter.run {
            repositoryClickedListener = this@VPGitHubRepositoriesListFragment
            submitList(result.toMutableList())
        }
    }

    private fun setUpToolbar() {
        setToolbarNavigationIcon(null)
        setOnToolbarNavigateUp(enabled = false)
    }

    private fun setUpView() {
        setToolbarTitle(R.string.vp_toolbar_results)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        rvRepositories.run {
            visibility = View.VISIBLE
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = repositoryAdapter
        }
        rvRepositories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = linearLayoutManager.childCount
                totalItemCount = linearLayoutManager.itemCount
                pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
                activityViewModel.onScroll(dy, visibleItemCount, totalItemCount, pastVisibleItems)
            }
        })
    }
}