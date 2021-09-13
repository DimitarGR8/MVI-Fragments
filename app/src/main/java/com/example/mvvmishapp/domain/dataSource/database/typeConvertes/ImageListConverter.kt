package com.example.mvvmishapp.domain.dataSource.database.typeConvertes

import androidx.room.TypeConverter
import com.example.mvvmishapp.domain.api.response.ImageModel
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class ImageListConverter {

    @TypeConverter
    fun listToJson(items: List<ImageModel>?): String? {
        if (items.isNullOrEmpty())
            return null
        return Json.encodeToString(ListSerializer(ImageModel.serializer()), items)
    }

    @TypeConverter
    fun jsonToList(data: String?): List<ImageModel>? {
        if (data.isNullOrEmpty())
            return null
        return Json.decodeFromString(ListSerializer(ImageModel.serializer()), data)
    }
}