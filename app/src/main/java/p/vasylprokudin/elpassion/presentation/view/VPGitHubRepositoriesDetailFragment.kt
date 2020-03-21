package p.vasylprokudin.elpassion.presentation.view

import p.vasylprokudin.elpassion.R
import p.vasylprokudin.elpassion.base.VPFragment

class VPGitHubRepositoriesDetailFragment : VPFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): VPGitHubRepositoriesDetailFragment = VPGitHubRepositoriesDetailFragment()
    }

    override val getLayoutResId: Int = R.layout.vp_fragment_github_repository_detail
}