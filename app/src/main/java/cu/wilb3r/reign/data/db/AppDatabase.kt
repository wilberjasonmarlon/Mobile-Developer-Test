package cu.wilb3r.reign.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cu.wilb3r.reign.data.db.dao.ArticleDAO
import cu.wilb3r.reign.data.db.dao.ArticleDeletedDAO
import cu.wilb3r.reign.data.db.models.DBArticle
import cu.wilb3r.reign.data.db.models.DBArticleDeleted
import cu.wilb3r.reign.utils.Constants.DATABASE_NAME
import cu.wilb3r.reign.utils.Constants.DB_VERSION

@Database(entities = [DBArticle::class, DBArticleDeleted::class], version = DB_VERSION, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDAO
    abstract fun articleDeletedDao(): ArticleDeletedDAO

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}