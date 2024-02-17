package com.example.assignments.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String


):Serializable {
    override fun hashCode(): Int {
        var result = id ?: 0 // Use elvis operator to provide default value if id is null
        result = 31 * result + url.hashCode() // No need to check if url is null in Kotlin
        return result
    }


}