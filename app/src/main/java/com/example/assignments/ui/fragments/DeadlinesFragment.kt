package com.example.assignments.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignments.R
import com.example.assignments.adapters.NAdapter
import com.example.assignments.databinding.FragmentArticleBinding
import com.example.assignments.databinding.FragmentDeadlinesBinding
import com.example.assignments.ui.HomeActivity
import com.example.assignments.ui.NewViewModels
import com.example.assignments.util.Constant
import com.example.assignments.util.Resource
import okhttp3.Response as Response1


class DeadlinesFragment : Fragment(R.layout.fragment_deadlines) {

    lateinit var newViewModels: NewViewModels
    lateinit var nAdapter: NAdapter
    lateinit var retriButton: Button
    lateinit var  errorText: TextView
    lateinit var itemHeadlineError: CardView
    lateinit var binding: FragmentDeadlinesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDeadlinesBinding.bind(view)

        itemHeadlineError = view.findViewById(R.id.itemHeadlinesError)
        val inflater  = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view : View = inflater.inflate(R.layout.item_error,null)

        retriButton = view.findViewById(R.id.retryButton)
        errorText = view.findViewById(R.id.errorText)

        newViewModels = (activity as HomeActivity).newViewModels
        setDeadLinesRecycler()


        nAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_deadlinesFragment_to_articleFragment, bundle)
        }
        newViewModels.headlines.observe(viewLifecycleOwner, Observer { response ->
            when(response){
            is Resource.Success<*> -> {
              hindProgressBar()
              hindErrorMessage()
              response.data?.let{nResponse ->
                  nAdapter.differ.submitList(nResponse.articles.toList())
                  val totalpages = nResponse.totalResults/Constant.QUERY_PAGE_SIZE+2
                  isLastPage = newViewModels.headlinesPage == totalpages
                  if(isLastPage){
                      binding.recyclerHeadlines.setPadding(0,0,0,0,)
                  }
              }
            }
                is Resource.Error<*> ->{
                hindProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "Sorry error $message",Toast.LENGTH_LONG).show()
                        showErrorMessage(message)
                    }
                }
                is Resource.Loading<*> ->{
               showProgressBar()
                }
        }

        })

        retriButton.setOnClickListener {
            newViewModels.getHeadlines("us")
        }
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private  fun hindProgressBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }
    private  fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading= true
    }
    private  fun hindErrorMessage(){
        itemHeadlineError.visibility = View.INVISIBLE
        isError= false
    }
    private  fun showErrorMessage(message: String){
        itemHeadlineError.visibility = View.VISIBLE
        errorText.text= message
    }
    val scrolListner = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoError = !isError
            val isNorLoadingAndNotLastPage = !isLastPage && !isLoading
            val isAtLLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val TotalMoreThanVisible = totalItemCount >= Constant.QUERY_PAGE_SIZE
            val shouldPaginate = isNoError && isNorLoadingAndNotLastPage && isAtLLastItem && isNotAtBeginning && isScrolling && TotalMoreThanVisible
            if (shouldPaginate) {
                newViewModels.getHeadlines("us")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }
        private  fun setDeadLinesRecycler(){
            nAdapter = NAdapter()
            binding.recyclerHeadlines.apply{
                adapter = nAdapter
                layoutManager = LinearLayoutManager(activity)
                addOnScrollListener(this@DeadlinesFragment.scrolListner)

        }
    }

}