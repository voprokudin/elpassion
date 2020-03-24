package p.vasylprokudin.elpassion.presentation.navigation

import androidx.fragment.app.FragmentManager
import p.vasylprokudin.elpassion.R
import p.vasylprokudin.elpassion.base.VPActivity
import p.vasylprokudin.elpassion.data.model.VPRawRepositories
import p.vasylprokudin.elpassion.presentation.view.VPGitHubRepositoriesDetailFragment
import p.vasylprokudin.elpassion.presentation.view.VPGitHubRepositoriesListFragment
import p.vasylprokudin.elpassion.util.fragment.VPFragmentUtil
import p.vasylprokudin.elpassion.data.model.VPRawRepositories.VPRawItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VPGitHubRepositoriesNavigator
@Inject constructor(
    private val activity: VPActivity,
    private val fragmentUtil: VPFragmentUtil
) {

    companion object {
        private const val FRAGMENT_CONTAINER = R.id.fragmentContainer
    }

    private val fragmentManager: FragmentManager by lazy { activity.supportFragmentManager }

    fun showGitHubRepositoriesListFragment() {
        if (isFragmentAlreadyShown<VPGitHubRepositoriesListFragment>()) return

        fragmentUtil.replaceFragmentAllowingStateLoss(
            fragmentManager = fragmentManager,
            fragment = VPGitHubRepositoriesListFragment.newInstance(),
            containerViewId = FRAGMENT_CONTAINER,
            addToBackStack = false
        )
    }

    fun showGitHubRepositoriesDetailsFragment(repository: VPRawItem) {
        if (isFragmentAlreadyShown<VPGitHubRepositoriesDetailFragment>()) return

        fragmentUtil.replaceFragmentAllowingStateLoss(
            fragmentManager = fragmentManager,
            fragment = VPGitHubRepositoriesDetailFragment.newInstance(repository),
            containerViewId = FRAGMENT_CONTAINER,
            addToBackStack = true
        )
    }

    fun clearBackStack() {
        fragmentManager.popBackStack()
    }

    fun isAnyFragmentVisible(): Boolean = fragmentUtil.findFragment(fragmentManager, FRAGMENT_CONTAINER) != null

    internal inline fun <reified T> isFragmentAlreadyShown() = fragmentUtil.findFragment(fragmentManager, FRAGMENT_CONTAINER) is T
}
