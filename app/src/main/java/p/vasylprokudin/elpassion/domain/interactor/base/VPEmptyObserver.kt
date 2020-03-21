package p.vasylprokudin.elpassion.domain.interactor.base

import io.reactivex.observers.DisposableObserver

open class VPEmptyObserver<T> : DisposableObserver<T>() {

    override fun onNext(t: T) {}

    override fun onError(e: Throwable) {}

    override fun onComplete() {}
}