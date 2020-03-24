package p.vasylprokudin.elpassion.data.rest

import io.reactivex.Single
import p.vasylprokudin.elpassion.data.model.VPRawRepositories
import retrofit2.http.GET
import retrofit2.http.Query

interface VPGitHubRepositoriesService {
    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<VPRawRepositories>
}