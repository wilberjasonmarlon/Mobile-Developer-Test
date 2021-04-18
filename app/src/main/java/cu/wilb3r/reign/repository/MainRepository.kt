package cu.wilb3r.reign.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cu.wilb3r.reign.data.api.ApiService
import cu.wilb3r.reign.data.db.models.DBArticle
import cu.wilb3r.reign.data.db.repository.DBRepository
import cu.wilb3r.reign.utils.Constants
import kotlinx.coroutines.flow.Flow

class MainRepository(
    private val articleRepository: DBRepository,
    private val apiService: ApiService
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getHits(): Flow<PagingData<DBArticle>> {
        return Pager(
            PagingConfig(pageSize = Constants.PAGE_SIZE, enablePlaceholders = false, prefetchDistance = 3),
            remoteMediator = ArticleRemoteMediator(apiService, articleRepository),
            pagingSourceFactory = { articleRepository.getAllArticles() }
        ).flow
    }
}