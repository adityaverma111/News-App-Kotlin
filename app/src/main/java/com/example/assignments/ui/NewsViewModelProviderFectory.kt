package com.example.assignments.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignments.repository.NRepository

class NewsViewModelProviderFectory(val app: Application, val nRepository :NRepository):ViewModelProvider.Factory{
    override fun<T:ViewModel>create(modelClass: Class<T>): T {
        return NewViewModels(app,nRepository)as T
    }

}