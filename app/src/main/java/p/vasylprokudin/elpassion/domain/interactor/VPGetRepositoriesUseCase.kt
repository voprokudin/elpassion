package p.vasylprokudin.elpassion.domain.interactor

import io.reactivex.Single
import p.vasylprokudin.elpassion.data.model.VPRawRepositories.VPRawItem
import p.vasylprokudin.elpassion.domain.interactor.base.VPSingleUseCase
import p.vasylprokudin.elpassion.domain.repository.VPGitHubRepositoriesRemoteRepository
import javax.inject.Inject

class VPGetRepositoriesUseCase
@Inject constructor(
    private val gitHubRepositoriesRemoteRepository: VPGitHubRepositoriesRemoteRepository
) : VPSingleUseCase<ArrayList<VPRawItem>, String>() {

    override fun buildUseCaseSingle(params: String?): Single<ArrayList<VPRawItem>> {
        params ?: return Single.error(IllegalStateException("search query must be provided"))

        return gitHubRepositoriesRemoteRepository.getRepositories(params)
    }
}