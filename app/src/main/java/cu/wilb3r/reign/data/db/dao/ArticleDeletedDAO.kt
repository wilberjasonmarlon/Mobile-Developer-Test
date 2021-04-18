package cu.wilb3r.reign.data.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import cu.wilb3r.reign.data.db.models.DBArticle
import cu.wilb3r.reign.data.db.models.DBArticleDeleted
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.selects.select


@Dao
interface ArticleDeletedDAO {

    // insert N articles
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(item: DBArticleDeleted)

    // Get all articles pagin
    @Query("SELECT * FROM article_delete_table")
    suspend fun getArticles(): List<DBArticleDeleted>
}