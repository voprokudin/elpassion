package p.vasylprokudin.elpassion.dependencyinjection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import p.vasylprokudin.elpassion.dependencyinjection.util.VPViewModelKey
import p.vasylprokudin.elpassion.presentation.viewmodel.VPGitHubRepositoriesDetailsViewModel
import p.vasylprokudin.elpassion.presentation.viewmodel.VPGitHubRepositoriesListViewModel
import p.vasylprokudin.elpassion.presentation.viewmodel.factory.VPViewModelFactory

@Module
abstract class VPViewModelModule {

    @Binds
    @IntoMap
    @VPViewModelKey(VPGitHubRepositoriesListViewModel::class)
    internal abstract fun bindGitHubRepositoriesListViewModel(gitHubRepositoriesListViewModel: VPGitHubRepositoriesListViewModel): ViewModel

    @Binds
    @IntoMap
    @VPViewModelKey(VPGitHubRepositoriesDetailsViewModel::class)
    internal abstract fun bindGitHubRepositoriesDetailsViewModel(gitHubRepositoriesDetailsViewModel: VPGitHubRepositoriesDetailsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: VPViewModelFactory): ViewModelProvider.Factory
}