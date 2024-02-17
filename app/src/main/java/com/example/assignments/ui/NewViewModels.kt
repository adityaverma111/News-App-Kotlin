package com.example.assignments.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.assignments.models.Article
import com.example.assignments.models.NResponse
import com.example.assignments.repository.NRepository
import com.example.assignments.util.Resource
import kotlinx.coroutines.launch
import okhttp3.Response
import java.io.IOException
import java.util.Locale.IsoCountryCode


class NewViewModels (app: Application, val nRepository: NRepository): AndroidViewModel(app){
    val headlines: MutableLiveData<Resource<NResponse>> = MutableLiveData()
    var headlinesPage = 1
    var headlinesResponse : NResponse? = null

    val searchNews: MutableLiveData<Resource<NResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searNewsNResponse: NResponse? = null
    var newsSearchQuery : String? = null
    var oldSearchQuery: String?= null


    init {
        getHeadlines("us")
    }
  fun getHeadlines(countryCode: String) = viewModelScope.launch {
      headlinesInternet(countryCode)
  }
    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNewsInternet(searchQuery)
    }

    private fun handleHeadlinesResponse(response : retrofit2.Response<NResponse>):Resource<NResponse>{
        if(response.isSuccessful){
            response.body()?.let{resultResponse ->
                headlinesPage++
                if(headlinesResponse == null){
                    headlinesResponse = resultResponse
                }else{
                    val oldArticles = headlinesResponse?.articles
                    val newArticles = resultResponse.articles
                    if (newArticles != null) {
                        oldArticles?.addAll(newArticles)
                    }
                }
                return Resource.Success(headlinesResponse ?: resultResponse)

            }
        }
        return Resource.Error(response.message())
    }
fun addFavourite(article: Article) = viewModelScope.launch {
    nRepository.upsert(article)
}
    fun getFavouriteNews() = nRepository.getFavouriteNews()
    fun deleteArticle(article: Article) = viewModelScope.launch {
        nRepository.deleteArticle(article)

}
    fun internetConnection(context : Context): Boolean{
        (context.getSystemService(Context.CONNECTIVITY_SERVICE)as ConnectivityManager).apply {
            return  getNetworkCapabilities(activeNetwork)?.run{
                when{
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->true
                    else -> false
                }
            }?: false
        }

    }



    private suspend fun headlinesInternet(countryCode: String){
        headlines.postValue(Resource.Loading())
        try{
            if(internetConnection(this.getApplication())){
                val response = nRepository.getHeadlines(countryCode,headlinesPage)
                headlines.postValue(handleHeadlinesResponse(response))
            }else{
                headlines.postValue(Resource.Error("NO internet connectivity"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException ->headlines.postValue(Resource.Error("unable to connect"))
                else -> headlines.postValue(Resource.Error("No signal"))
            }
        }
    }
    private suspend fun  searchNewsInternet(searchQuery: String){
        newsSearchQuery = searchQuery
        headlines.postValue(Resource.Loading())
        try{
            if(internetConnection(this.getApplication())){
             //   val response = nRepository. searchNews(searchQuery,searchNewsPage)
              //  searchNews.postValue(handleHeadlinesResponse(response))
            }else{
                searchNews.postValue(Resource.Error("NO internet connectivity"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException ->searchNews.postValue(Resource.Error("unable to connect"))
                else -> searchNews.postValue(Resource.Error("No signal"))
            }
        }
    }
}


