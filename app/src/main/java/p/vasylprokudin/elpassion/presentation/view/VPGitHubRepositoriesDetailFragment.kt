package p.vasylprokudin.elpassion.presentation.view

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.vp_fragment_github_repository_detail.*
import p.vasylprokudin.elpassion.C
import p.vasylprokudin.elpassion.R
import p.vasylprokudin.elpassion.base.VPFragment
import p.vasylprokudin.elpassion.data.model.VPRawRepositories.VPRawItem
import p.vasylprokudin.elpassion.extensions.argNotNull

class VPGitHubRepositoriesDetailFragment : VPFragment() {

    companion object {
        @JvmStatic
        fun newInstance(repository: VPRawItem) : VPGitHubRepositoriesDetailFragment {
            val fragment = VPGitHubRepositoriesDetailFragment()
            val args = Bundle().apply {
                putParcelable(C.BundleArgs.REPOSITORY, repository)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private val repository: VPRawItem by argNotNull(C.BundleArgs.REPOSITORY)

    override val getLayoutResId: Int = R.layout.vp_fragment_github_repository_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpView()
    }

    private fun setUpToolbar() {
        setToolbarNavigationIcon(R.drawable.ic_back)
        setOnToolbarNavigateUp(enabled = true)
        setToolbarTitle(repository.name)
    }

    private fun setUpView() {
        repository.apply {
            Glide.with(this@VPGitHubRepositoriesDetailFragment).load(owner.avatar_url).into(ivAvatar)
            tvDescription.text = description
            tvUsername.text = full_name
            tvWatchers.text = watchers.toString()
            tvLanguage.text = language
        }
    }
}