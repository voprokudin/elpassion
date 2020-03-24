package p.vasylprokudin.elpassion.domain.repository

import io.reactivex.Single
import p.vasylprokudin.elpassion.data.model.VPRawRepositories.VPRawItem

interface VPGitHubRepositoriesRemoteRepository {

    fun getRepositories(query: String): Single<ArrayList<VPRawItem>>
}