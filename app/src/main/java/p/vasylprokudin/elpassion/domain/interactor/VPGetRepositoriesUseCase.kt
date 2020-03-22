package p.vasylprokudin.elpassion.domain.interactor

import io.reactivex.Single
import p.vasylprokudin.elpassion.data.model.VPRawRepositories
import p.vasylprokudin.elpassion.domain.interactor.base.VPSingleUseCase
import p.vasylprokudin.elpassion.domain.repository.VPGitHubRepositoriesRemoteRepository
import javax.inject.Inject

class VPGetRepositoriesUseCase
@Inject constructor(
    private val gitHubRepositoriesRemoteRepository: VPGitHubRepositoriesRemoteRepository
) : VPSingleUseCase<VPRawRepositories, Unit>() {

    override fun buildUseCaseSingle(params: Unit?): Single<VPRawRepositories> =
        gitHubRepositoriesRemoteRepository.getRepositories("ababababababada")
}