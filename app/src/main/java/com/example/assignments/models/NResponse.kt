package com.example.assignments.models

import com.example.assignments.models.Article

data class NResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)