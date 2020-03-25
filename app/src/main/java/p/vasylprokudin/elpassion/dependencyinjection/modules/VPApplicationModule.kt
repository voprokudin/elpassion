package p.vasylprokudin.elpassion.dependencyinjection.modules

import dagger.Module
import dagger.Provides
import p.vasylprokudin.elpassion.base.VPActivity
import p.vasylprokudin.elpassion.data.repository.VPGitHubRepositoriesRemoteRepositoryImpl
import p.vasylprokudin.elpassion.data.rest.VPGitHubRepositoriesService
import p.vasylprokudin.elpassion.domain.repository.VPGitHubRepositoriesRemoteRepository
import p.vasylprokudin.elpassion.presentation.navigation.VPGitHubRepositoriesNavigator
import p.vasylprokudin.elpassion.presentation.view.list.adapter.VPRepositoriesAdapter
import p.vasylprokudin.elpassion.util.fragment.VPFragmentUtil
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [VPViewModelModule::class])
class VPApplicationModule {

    private val BASE_URL = "https://api.github.com/"

    @Provides
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    internal fun provideRetrofitService(retrofit: Retrofit): VPGitHubRepositoriesService {
        return retrofit.create<VPGitHubRepositoriesService>(VPGitHubRepositoriesService::class.java)
    }

    @Provides
    internal fun provideVPFragmentUtil(): VPFragmentUtil = VPFragmentUtil()

    @Provides
    internal fun provideGitHubRepositoriesRemoteRepository(
        gitHubRepositoriesRemoteRepositoryImpl: VPGitHubRepositoriesRemoteRepositoryImpl
    ): VPGitHubRepositoriesRemoteRepository = gitHubRepositoriesRemoteRepositoryImpl

    @Provides
    internal fun provideVPGitHubRepositoriesNavigator(
        activity: VPActivity,
        fragmentUtil: VPFragmentUtil
    ): VPGitHubRepositoriesNavigator = VPGitHubRepositoriesNavigator(activity, fragmentUtil)

    @Provides
    internal fun provideRepositoriesAdapter(): VPRepositoriesAdapter = VPRepositoriesAdapter()
}