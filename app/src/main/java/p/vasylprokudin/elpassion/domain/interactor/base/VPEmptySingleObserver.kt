package p.vasylprokudin.elpassion.domain.interactor.base

import io.reactivex.observers.DisposableSingleObserver

open class VPEmptySingleObserver<T> : DisposableSingleObserver<T>() {

    override fun onSuccess(result: T) {
    }

    override fun onError(throwable: Throwable) {
    }
}