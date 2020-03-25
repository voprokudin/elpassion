package p.vasylprokudin.elpassion.dependencyinjection.modules

import dagger.Binds
import dagger.Module
import p.vasylprokudin.elpassion.base.VPActivity
import p.vasylprokudin.elpassion.presentation.view.VPHomeActivity

@Module
abstract class VPHomeActivityModule {

    @Binds
    abstract fun providesActivity(activity: VPHomeActivity): VPActivity
}