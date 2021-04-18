package cu.wilb3r.reign.data.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentText(
    val fullyHighlighted: Boolean?,
    val matchLevel: String?,
    val matchedWords: List<String>?,
    val value: String?
): Parcelable