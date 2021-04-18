package cu.wilb3r.reign.di

import cu.wilb3r.reign.data.api.ApiService
import cu.wilb3r.reign.data.db.AppDatabase
import cu.wilb3r.reign.data.db.repository.DBRepository
import cu.wilb3r.reign.repository.ArticleRemoteMediator
import cu.wilb3r.reign.repository.MainRepository
import cu.wilb3r.reign.ui.adapter.ArticlePageAdapter
import cu.wilb3r.reign.ui.home.HomeViewModel
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val dataBaseModule = module {
    single { AppDatabase(get()) }
    single { DBRepository(get()) }
}

val networkModule = module {
    single { HttpLoggingInterceptor.Logger.DEFAULT }
    single { HttpLoggingInterceptor(get()) }
    single { ApiService(get()) }
}

val repositoryModule = module {
    single { MainRepository(get(), get())}
    single { ArticleRemoteMediator (get(), get())}
}

val uiModule = module {
    single { ArticlePageAdapter() }
    viewModel { HomeViewModel(get(), get()) }
}
