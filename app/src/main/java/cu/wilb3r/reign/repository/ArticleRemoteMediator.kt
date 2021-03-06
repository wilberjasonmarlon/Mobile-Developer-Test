package cu.wilb3r.reign.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import cu.wilb3r.reign.data.api.ApiService
import cu.wilb3r.reign.data.db.models.DBArticle
import cu.wilb3r.reign.data.db.repository.DBRepository
import cu.wilb3r.reign.utils.Constants.FIRST_PAGE
import cu.wilb3r.reign.utils.Constants.HITS_PER_PAGE
import cu.wilb3r.reign.utils.Constants.PAGE
import cu.wilb3r.reign.utils.Constants.PAGE_SIZE
import cu.wilb3r.reign.utils.Constants.QUERY
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val api: ApiService,
    private val repo: DBRepository
): RemoteMediator<Int, DBArticle>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DBArticle>
    ): MediatorResult {
        // we start to request from older record to recent Constants.LAST_PAGE - 1 until 0
        return try {
            val page = when(loadType){
                LoadType.REFRESH -> FIRST_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    getKeys() ?: FIRST_PAGE
                }
            }
            println(">> Page: $page")
            val param  = HashMap<String, String>().apply {
                put(QUERY, "mobile")
                put(PAGE, "$page")
                put(HITS_PER_PAGE, "$PAGE_SIZE")
            }
            val response = api.getHits(param)
            val hits = response.body()?.hits
            val p = response.body()?.page
            val np = response.body()?.nbPages
            if(hits != null) {
                val articles = ArrayList<DBArticle>()
                for(item in hits){
                    articles.reverse()
                    articles.add(item.toEntity(page))
                }
                if(page == FIRST_PAGE)
                    repo.cleanInsertArticles(articles)
                else
                    repo.insertArticles(articles)
            }

            MediatorResult.Success(endOfPaginationReached = p == np)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeys(): Int? {
        return repo.getKeys().lastOrNull()?.page?.plus(1)
    }
}