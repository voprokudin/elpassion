package p.vasylprokudin.elpassion.dependencyinjection.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import p.vasylprokudin.elpassion.presentation.view.VPHomeActivity

@Module
abstract class VPActivityBuildersModule {

    @ContributesAndroidInjector(modules = [
        VPFragmentsBindingModule::class,
        VPHomeActivityModule::class,
        VPApplicationModule::class
    ])
    abstract fun contributeHomeActivity(): VPHomeActivity
}