package cu.wilb3r.reign.data.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class HitsResponse(
    val exhaustiveNbHits: Boolean?,
    val hits: List<Hit>?,
    val hitsPerPage: Int?,
    val nbHits: Int?,
    val nbPages: Int?,
    val page: Int?,
    val params: String?,
    val processingTimeMS: Int?,
    val query: String?
): Parcelable