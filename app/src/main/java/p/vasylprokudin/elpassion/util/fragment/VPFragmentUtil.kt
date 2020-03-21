package p.vasylprokudin.elpassion.util.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import p.vasylprokudin.elpassion.base.VPFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VPFragmentUtil
@Inject constructor() {

    fun replaceFragmentAllowingStateLoss(
        fragmentManager: FragmentManager,
        fragment: VPFragment,
        @IdRes containerViewId: Int,
        addToBackStack: Boolean
    ) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerViewId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commitAllowingStateLoss()
    }

    fun findFragment(fragmentManager: FragmentManager, @IdRes fragmentContainerId: Int): Fragment?
            = fragmentManager.findFragmentById(fragmentContainerId)
}