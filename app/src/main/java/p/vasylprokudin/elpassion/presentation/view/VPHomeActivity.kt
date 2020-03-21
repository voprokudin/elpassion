package p.vasylprokudin.elpassion.presentation.view

import android.os.Bundle
import p.vasylprokudin.elpassion.R
import p.vasylprokudin.elpassion.base.VPActivity
import p.vasylprokudin.elpassion.presentation.navigation.VPGitHubRepositoriesNavigator
import javax.inject.Inject

class VPHomeActivity : VPActivity() {

    @Inject
    lateinit var navigator: VPGitHubRepositoriesNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vp_activity_home)
        navigator.showGitHubRepositoriesListFragment()
    }
}
