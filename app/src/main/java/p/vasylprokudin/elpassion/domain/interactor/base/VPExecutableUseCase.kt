package p.vasylprokudin.elpassion.domain.interactor.base

import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver

interface VPExecutableUseCase {

    interface Single<Results, in Params> {

        fun execute(
            observer: DisposableSingleObserver<Results> = VPEmptySingleObserver(),
            params: Params? = null
        )
    }

    interface Observable<Results, in Params> {
        fun execute(
            observer: DisposableObserver<Results> = VPEmptyObserver<Results>(),
            params: Params? = null
        )
    }
}