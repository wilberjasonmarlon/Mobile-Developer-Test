package cu.wilb3r.reign.data.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import cu.wilb3r.reign.data.db.models.DBArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.selects.select


@Dao
interface ArticleDAO {

    // insert N articles
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(items: List<DBArticle>)

    // insert One article
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(item: DBArticle)


    // Get all articles pagin
    @Query("SELECT * FROM article_table WHERE objectID NOT IN article_delete_table ORDER BY created_at_i DESC")
    fun getAllArticles(): PagingSource<Int, DBArticle>

    // the keys
    @Query("SELECT * FROM article_table ORDER BY page ASC")
    suspend fun getKeys(): List<DBArticle>

    @Transaction
    suspend fun cleanInsert(item: List<DBArticle>) {
        cleanAllArticles()
        insertArticles(item)
    }

    @Query("DELETE FROM article_table")
    fun cleanAllArticles(): Int

    //@Query("SELECT * FROM article_table WHERE deleted == 0 ORDER BY created_at DESC")
//    @Query("SELECT * FROM article_table WHERE deleted == 0")
//    fun observeAllArticles(): Flow<List<DBArticle>>




//
//    @Query("DELETE FROM article_table")
//    fun deleteAllArticles(): Int
//
//    //delete unSync, just call after softInsert() func
//    @Query("DELETE FROM article_table WHERE sync = 0 AND deleted = 0")
//    fun deleteUnsycArticles(): Int

    /*// find an article in deleted state
    @Query("SELECT * FROM article_table WHERE objectID = :articleId AND deleted = 1")
    suspend fun findDeleted(articleId: String): List<DBArticle>
*/



    /*@Transaction
    suspend fun softInsert(items: List<DBArticle>) {
        unSetAsSync()
        items.forEach {
            if (findDeleted(it.objectID).isEmpty())
                insertArticle(it)
        }
        deleteUnsycArticles()
    }*/

    // set all as deleted
    @Query("UPDATE article_table SET deleted = 1 WHERE objectID = :articleId")
    suspend fun setAsDelete(articleId: String)

    // unset all as sync
    @Query("UPDATE article_table SET sync = 0")
    fun unSetAsSync()

    suspend fun softDeleteArticle(item: DBArticle) {
        setAsDelete(item.objectID)
    }

}