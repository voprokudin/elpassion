package p.vasylprokudin.elpassion.domain.repository

import io.reactivex.Single
import p.vasylprokudin.elpassion.data.model.VPRawRepositories

interface VPGitHubRepositoriesRemoteRepository {

    fun getRepositories(query: String): Single<VPRawRepositories>
}