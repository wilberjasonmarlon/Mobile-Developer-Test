package cu.wilb3r.reign.utils

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val API_URL = "https://hn.algolia.com/api/v1/"
    const val SEARCH_BY_DATE = "search_by_date"

    const val PAGE_SIZE = 50
    const val NO_PAGE = -1
    const val FIRST_PAGE = 0
    const val LAST_PAGE = 10
    const val PER_PAGE = 10
    const val QUERY = "query"
    const val PAGE = "page"
    const val HITS_PER_PAGE = "hitsPerPage"
    const val NB_PAGES = "nbPages"
    const val NB_PAGES_SIZE = 10


    const val DATABASE_NAME = "articles_db"
    const val DB_VERSION = 1
    const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
}