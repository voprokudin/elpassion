package p.vasylprokudin.elpassion.data.repository

import p.vasylprokudin.elpassion.data.rest.VPGitHubRepositoriesService
import p.vasylprokudin.elpassion.domain.repository.VPGitHubRepositoriesRemoteRepository
import javax.inject.Inject

class VPGitHubRepositoriesRemoteRepositoryImpl
@Inject constructor(
    private val gitHubRepositoriesService: VPGitHubRepositoriesService
) : VPGitHubRepositoriesRemoteRepository {

    override fun getRepositories(query: String) = gitHubRepositoriesService.searchRepositories(query)
}