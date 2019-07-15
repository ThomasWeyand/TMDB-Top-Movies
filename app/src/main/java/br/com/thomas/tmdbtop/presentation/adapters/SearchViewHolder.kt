package br.com.thomas.tmdbtop.presentation.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.thomas.tmdbtop.databinding.SearchItemBinding
import br.com.thomas.tmdbtop.domain.models.Movie
import br.com.thomas.tmdbtop.presentation.ListHomeInterface

class SearchViewHolder (view: View, context : Context, private val homeInterface: ListHomeInterface,
                        private val movieList: ArrayList<Movie>) : RecyclerView.ViewHolder(view){

    val binding: SearchItemBinding? = DataBindingUtil.bind(view)

    init {
        binding?.posterImg?.getLayoutParams()?.width =
            (context.getResources().getDisplayMetrics().widthPixels * 0.31).toInt()
        binding?.posterImg?.getLayoutParams()?.height =
            (context.getResources().getDisplayMetrics().widthPixels * 0.31 / 0.66).toInt()
        view.setOnClickListener{
            homeInterface.movieSelected(movieList.get(adapterPosition))
        }
    }
}