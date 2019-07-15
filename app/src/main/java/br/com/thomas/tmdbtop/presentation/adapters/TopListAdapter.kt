package br.com.thomas.tmdbtop.presentation.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.thomas.tmdbtop.R
import br.com.thomas.tmdbtop.domain.models.Movie
import br.com.thomas.tmdbtop.presentation.ListHomeInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class TopListAdapter (var context : Context, var moviesList: ArrayList<Movie>, val homeInterface: ListHomeInterface) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun swapData(movies : List<Movie>){
        moviesList.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ToplistViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.top_list_item,parent,false),context, homeInterface,moviesList)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = moviesList.get(position)
        val binding = (holder as ToplistViewHolder).binding
        val imgUrl = context.resources.getString(R.string.image_base_342) + movie.poster_path

        Glide
            .with(context)
            .load(imgUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding?.thumbImg!!);

        binding.movie = moviesList.get(position)
        binding.executePendingBindings()
    }
}