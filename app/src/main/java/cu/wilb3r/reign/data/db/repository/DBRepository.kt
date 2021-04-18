package cu.wilb3r.reign.data.db.repository

import androidx.paging.PagingSource
import androidx.room.withTransaction
import cu.wilb3r.reign.data.db.AppDatabase
import cu.wilb3r.reign.data.db.models.DBArticle
import kotlinx.coroutines.flow.Flow

class DBRepository(private val db: AppDatabase) {

    suspend fun insertArticles(items: ArrayList<DBArticle> ) = db.withTransaction {
        db.articleDao().insertArticles(items)
    }

    suspend fun cleanInsertArticles(items: ArrayList<DBArticle>) = db.articleDao().cleanInsert(items)

    suspend fun getKeys(): List<DBArticle> = db.articleDao().getKeys()

    //fun observeAllArticles(): Flow<List<DBArticle>> = db.articleDao().observeAllArticles()

    fun getAllArticles(): PagingSource<Int, DBArticle> = db.articleDao().getAllArticles()

    suspend fun softDeleteArticle(item: DBArticle) = db.withTransaction {
        db.articleDao().softDeleteArticle(item)
        db.articleDeletedDao().insertArticle(item.toDelete(item))
    }

}