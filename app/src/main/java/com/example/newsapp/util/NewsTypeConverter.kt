package com.example.newsapp.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.newsapp.data.local.entity.SourceEntity

@ProvidedTypeConverter
class NewsTypeConverter {
    @TypeConverter
    fun sourceToString(sourceEntity: SourceEntity): String {
        return "${sourceEntity.id},${sourceEntity.name}"
    }

    @TypeConverter
    fun stringToSource(sourceString: String): SourceEntity {
        val source = sourceString.split(",")
        return SourceEntity(source[0], source[1])
    }
}