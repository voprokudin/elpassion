package p.vasylprokudin.elpassion.dependencyinjection.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import p.vasylprokudin.elpassion.presentation.view.VPGitHubRepositoriesDetailFragment
import p.vasylprokudin.elpassion.presentation.view.VPGitHubRepositoriesListFragment

@Module
abstract class VPFragmentsBindingModule {

    @ContributesAndroidInjector
    internal abstract fun provideGitHubRepositoriesListFragment(): VPGitHubRepositoriesListFragment

    @ContributesAndroidInjector
    internal abstract fun provideGitHubRepositoriesDetailFragment(): VPGitHubRepositoriesDetailFragment
}