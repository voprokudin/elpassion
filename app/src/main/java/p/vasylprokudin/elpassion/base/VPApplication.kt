package p.vasylprokudin.elpassion.base

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import p.vasylprokudin.elpassion.dependencyinjection.components.DaggerApplicationComponent

class VPApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)

        return component
    }
}