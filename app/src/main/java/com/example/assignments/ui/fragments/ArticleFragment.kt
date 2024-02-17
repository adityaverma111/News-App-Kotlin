package com.example.assignments.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.assignments.R
import com.example.assignments.databinding.ActivityHomeBinding
import com.example.assignments.databinding.FragmentArticleBinding
import com.example.assignments.models.Article
import com.example.assignments.ui.HomeActivity
import com.example.assignments.ui.NewViewModels
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.NavArgs as NavArgs1


class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var  newViewModel : NewViewModels
    val args: ArticleFragmentArgs by this.navArgs()
    lateinit var  binding: FragmentArticleBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        newViewModel = (activity as HomeActivity).newViewModels
        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url.let {
                if (it != null) {
                    loadUrl(it)
                }
            }
        }
        binding.fab.setOnClickListener{
            newViewModel.addFavourite(article)
            Snackbar.make(view,"Added to favourite",Snackbar.LENGTH_SHORT).show()
        }
    }

}