package com.example.assignments.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignments.R
import com.example.assignments.adapters.NAdapter
import com.example.assignments.databinding.FragmentFavouriteBinding
import com.example.assignments.ui.HomeActivity
import com.example.assignments.ui.NewViewModels
import com.google.android.material.snackbar.Snackbar


class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    lateinit var newViewModels: NewViewModels
    lateinit var nAdapter: NAdapter
    lateinit var binding: FragmentFavouriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouriteBinding.bind(view)



        newViewModels = (activity as HomeActivity).newViewModels
        setupFavouriteRecycler()

        nAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_deadlinesFragment_to_articleFragment, bundle)
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return  true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = nAdapter.differ.currentList[position]
                newViewModels.deleteArticle(article)
                Snackbar.make(view,"Remove all favourites",Snackbar.LENGTH_LONG).apply {
                    setAction("undo"){
                        newViewModels.addFavourite(article)
                    }
                    show()
                }
            }
        }

         ItemTouchHelper(itemTouchHelper).apply {
             attachToRecyclerView(binding.recyclerFavourites)
         }
         newViewModels.getFavouriteNews().observe(viewLifecycleOwner, Observer { articles ->
             nAdapter.differ.submitList(articles)
         })
    }

    private fun setupFavouriteRecycler() {
        nAdapter = NAdapter()
        binding.recyclerFavourites.apply {
            adapter = nAdapter
            layoutManager = LinearLayoutManager(activity)

        }

    }
}
