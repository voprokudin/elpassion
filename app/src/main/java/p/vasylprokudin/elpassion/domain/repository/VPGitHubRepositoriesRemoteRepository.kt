package p.vasylprokudin.elpassion.domain.repository

import io.reactivex.Single
import p.vasylprokudin.elpassion.data.model.VPRawRepositories.VPRawItem
import p.vasylprokudin.elpassion.domain.model.VPSearchParams

interface VPGitHubRepositoriesRemoteRepository {

    fun getRepositories(searchParams: VPSearchParams): Single<ArrayList<VPRawItem>>
}