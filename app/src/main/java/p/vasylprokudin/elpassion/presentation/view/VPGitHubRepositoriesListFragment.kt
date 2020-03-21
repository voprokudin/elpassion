package p.vasylprokudin.elpassion.presentation.view

import p.vasylprokudin.elpassion.R
import p.vasylprokudin.elpassion.base.VPFragment

class VPGitHubRepositoriesListFragment : VPFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): VPGitHubRepositoriesListFragment = VPGitHubRepositoriesListFragment()
    }

    override val getLayoutResId: Int = R.layout.vp_fragment_github_repositories_list
}