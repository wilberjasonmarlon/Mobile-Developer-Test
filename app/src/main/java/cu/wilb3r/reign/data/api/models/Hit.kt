package cu.wilb3r.reign.data.api.models

import android.os.Parcelable
import cu.wilb3r.reign.data.db.models.DBArticle
import cu.wilb3r.reign.utils.Constants.DATE_PATTERN
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Hit(
    val author: String?,
    val title: String?,
    val comment_text: String?,
    val created_at: String?,
    val created_at_i: Long?,
    val objectID: String?,
    val parent_id: Int? = 0,
    val story_id: Int? = 0,
    val story_title: String? = "",
    val story_url: String? = "",
    var deleted: Boolean? = false,
    var sync: Boolean? = true
): Parcelable {
    // Create Entity from this object
    fun toEntity(ppage: Int = 0): DBArticle {
        val dateFormat = SimpleDateFormat(DATE_PATTERN, Locale.ROOT)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = created_at?.let { dateFormat.parse(it) }
                ?: dateFormat.parse(Calendar.getInstance().toString())
        return DBArticle(
                author,
                title,
                comment_text,
                date,
                created_at_i,
                objectID!!,
                parent_id,
                story_id,
                story_title,
                story_url,
                page = ppage
        )
    }
}