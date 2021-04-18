package cu.wilb3r.reign.data.db.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlinx.parcelize.Parcelize

@Entity(tableName = "article_table")
@Parcelize
data class DBArticle (
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("comment_text") val comment_text: String?,
    @SerializedName("created_at") val created_at: Date?,
    @SerializedName("created_at_i") val created_at_i: Long?,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("objectID") val objectID: String,
    @SerializedName("parent_id") val parent_id: Int?,
    @SerializedName("story_id") val story_id: Int?,
    @SerializedName("story_title") val story_title: String?,
    @SerializedName("story_url") val story_url: String?,
    @SerializedName("deleted") var deleted: Boolean? = false,
    @SerializedName("sync") var sync: Boolean? = true,
    @SerializedName("page") var page: Int?
): Parcelable {
    companion object {
        private val LOADING = DBArticle(
            "loading",
            "loading",
            "loading",
            null,
            null,
            "loading",
            0,
            0,
            "loading",
            "loading",
            false,
            false,
            0
        )
        val LOADING_LIST = arrayListOf(
            LOADING, LOADING, LOADING, LOADING, LOADING, LOADING,
            LOADING, LOADING, LOADING, LOADING, LOADING, LOADING,
            LOADING, LOADING, LOADING, LOADING, LOADING, LOADING,
            LOADING, LOADING, LOADING, LOADING, LOADING, LOADING,
            LOADING, LOADING, LOADING, LOADING, LOADING, LOADING,
        )
    }
    fun toDelete(item: DBArticle) = DBArticleDeleted(item.objectID)
}