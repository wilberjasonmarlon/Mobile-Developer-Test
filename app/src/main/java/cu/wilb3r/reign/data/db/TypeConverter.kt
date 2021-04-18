package cu.wilb3r.reign.data.db

import androidx.room.TypeConverter
import java.util.*

class TypeConverter {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

}