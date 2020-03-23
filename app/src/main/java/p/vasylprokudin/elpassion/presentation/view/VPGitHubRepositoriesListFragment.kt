package p.vasylprokudin.elpassion.presentation.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.vp_fragment_github_repositories_list.*
import p.vasylprokudin.elpassion.C
import p.vasylprokudin.elpassion.R
import p.vasylprokudin.elpassion.base.VPFragment
import p.vasylprokudin.elpassion.data.model.VPRawRepositories
import p.vasylprokudin.elpassion.extensions.argNotNull
import p.vasylprokudin.elpassion.presentation.view.list.adapter.VPRepositoriesAdapter
import p.vasylprokudin.elpassion.presentation.view.list.listener.VPRepositoryClickedListener
import p.vasylprokudin.elpassion.data.model.VPRawRepositories.VPRawItem
import javax.inject.Inject

class VPGitHubRepositoriesListFragment : VPFragment(), VPRepositoryClickedListener {

    companion object {
        @JvmStatic
        fun newInstance(result: VPRawRepositories) : VPGitHubRepositoriesListFragment {
            val fragment = VPGitHubRepositoriesListFragment()
            val args = Bundle().apply {
                putParcelable(C.BundleArgs.REPOSITORIES, result)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private val rawRepository: VPRawRepositories by argNotNull(C.BundleArgs.REPOSITORIES)

    @Inject
    lateinit var repositoryAdapter: VPRepositoriesAdapter

    override val getLayoutResId: Int = R.layout.vp_fragment_github_repositories_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun onRepositoryRowClicked(repository: VPRawItem) {
    }

    private fun setupView() {
        setToolbarTitle(R.string.vp_toolbar_results)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        repositoryAdapter.run {
            repositoryClickedListener = this@VPGitHubRepositoriesListFragment
            submitList(rawRepository.items)
        }
        rvRepositories.run {
            visibility = View.VISIBLE
            setHasFixedSize(true)
            adapter = repositoryAdapter
        }
    }
}