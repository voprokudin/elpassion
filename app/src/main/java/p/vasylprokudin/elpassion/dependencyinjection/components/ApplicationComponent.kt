package p.vasylprokudin.elpassion.dependencyinjection.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import p.vasylprokudin.elpassion.base.VPApplication
import p.vasylprokudin.elpassion.dependencyinjection.modules.VPActivityBuildersModule
import p.vasylprokudin.elpassion.dependencyinjection.modules.VPApplicationModule
import p.vasylprokudin.elpassion.dependencyinjection.modules.VPContextModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    VPActivityBuildersModule::class,
    VPApplicationModule::class,
    VPContextModule::class
])
interface ApplicationComponent : AndroidInjector<VPApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}