package p.vasylprokudin.elpassion.domain.interactor.base

import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver

abstract class VPObservableUseCase<Results, in Params> :
    VPBaseReactiveUseCase(),
    VPExecutableUseCase.Observable<Results, Params> {

    abstract fun buildUseCaseObservable(params: Params? = null): Observable<Results>

    override fun execute(
        observer: DisposableObserver<Results>,
        params: Params?
    ) {
        val observable = buildUseCaseObservableWithSchedulers(params)
        addDisposable(observable.subscribeWith(observer))
    }

    private fun buildUseCaseObservableWithSchedulers(params: Params?): Observable<Results> = buildUseCaseObservable(params)
            .subscribeOn(threadExecutorScheduler)
            .observeOn(postExecutionThreadScheduler)
}