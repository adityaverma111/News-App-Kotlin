package com.example.assignments.repository

import com.example.assignments.api.RetrofitInstence
import com.example.assignments.db.ArticleDataBase
import com.example.assignments.models.Article
import retrofit2.http.Query
import java.util.Locale.IsoCountryCode

class NRepository(val db : ArticleDataBase) {
    suspend fun getHeadlines(countryCode:String , pageNumber:Int)=
        RetrofitInstence.api.getHeadlines(countryCode,pageNumber)
    //suspend fun searchNews(searchQuery: String,pageNumber:Int)=
     //   RetrofitInstence.api.searchForNews(searchQuery,pageNumber)
    suspend fun upsert(article:Article) = db.getArticleDao().upsert(article)

    fun getFavouriteNews() = db.getArticleDao().getAllArticle()

    suspend fun  deleteArticle(article:Article)=db.getArticleDao().deleteArticle(article)

}