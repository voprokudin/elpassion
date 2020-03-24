package p.vasylprokudin.elpassion.data.repository

import io.reactivex.Single
import p.vasylprokudin.elpassion.data.rest.VPGitHubRepositoriesService
import p.vasylprokudin.elpassion.domain.model.VPSearchParams
import p.vasylprokudin.elpassion.domain.repository.VPGitHubRepositoriesRemoteRepository
import p.vasylprokudin.elpassion.data.model.VPRawRepositories.VPRawItem
import javax.inject.Inject

class VPGitHubRepositoriesRemoteRepositoryImpl
@Inject constructor(
    private val gitHubRepositoriesService: VPGitHubRepositoriesService
) : VPGitHubRepositoriesRemoteRepository {

    override fun getRepositories(searchParams: VPSearchParams): Single<ArrayList<VPRawItem>> =
        gitHubRepositoriesService.searchRepositories(
            query = searchParams.query,
            page = searchParams.page,
            perPage = searchParams.perPage
        ).map { it.items }
}