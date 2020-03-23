package p.vasylprokudin.elpassion.dependencyinjection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import p.vasylprokudin.elpassion.dependencyinjection.util.VPViewModelKey
import p.vasylprokudin.elpassion.presentation.viewmodel.VPHomeViewModel
import p.vasylprokudin.elpassion.presentation.viewmodel.factory.VPViewModelFactory

@Module
abstract class VPViewModelModule {

    @Binds
    @IntoMap
    @VPViewModelKey(VPHomeViewModel::class)
    internal abstract fun bindHomeViewModel(homeViewModel: VPHomeViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: VPViewModelFactory): ViewModelProvider.Factory
}