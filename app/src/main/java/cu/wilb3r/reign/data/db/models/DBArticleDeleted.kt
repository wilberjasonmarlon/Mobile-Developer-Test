package cu.wilb3r.reign.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "article_delete_table")
data class DBArticleDeleted(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("objectID") val objectID: String
)
