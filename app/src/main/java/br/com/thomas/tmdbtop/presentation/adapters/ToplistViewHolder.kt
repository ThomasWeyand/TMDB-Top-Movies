package br.com.thomas.tmdbtop.presentation.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.thomas.tmdbtop.databinding.TopListItemBinding
import br.com.thomas.tmdbtop.domain.models.Movie
import br.com.thomas.tmdbtop.presentation.ListHomeInterface

class ToplistViewHolder(view: View, context : Context, private val homeInterface: ListHomeInterface,private val movieList: ArrayList<Movie>)
    : RecyclerView.ViewHolder(view) {

    val binding: TopListItemBinding? = DataBindingUtil.bind(view)

    init {

        binding?.thumbImg?.layoutParams?.width = (context.resources.displayMetrics.widthPixels * 0.31).toInt()
        binding?.thumbImg?.layoutParams?.height = ((context.resources.displayMetrics.widthPixels * 0.31) / 0.66).toInt()
        view.setOnClickListener {
            homeInterface.movieSelected(movieList.get(adapterPosition))
        }
       /* binding?.itemContainer?.setOnClickListener { object :  View.OnClickListener{
            override fun onClick(p0: View?) {
            }
        }}*/

    }

}