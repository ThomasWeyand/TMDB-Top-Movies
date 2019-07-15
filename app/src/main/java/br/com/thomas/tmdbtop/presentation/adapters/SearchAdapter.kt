package br.com.thomas.tmdbtop.presentation.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.thomas.tmdbtop.R
import br.com.thomas.tmdbtop.domain.models.Movie
import br.com.thomas.tmdbtop.domain.models.SearchModel
import br.com.thomas.tmdbtop.presentation.ListHomeInterface
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.text.SimpleDateFormat
import java.util.*

class SearchAdapter(val context : Context, val searchList : ArrayList<Movie>,
                    private val homeInterface: ListHomeInterface) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    fun swapData(movies : List<Movie>){
        searchList.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item,parent,false),context,homeInterface,searchList)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = searchList.get(position)
        val binding = (holder as SearchViewHolder).binding
        val imgUrl = context.resources.getString(R.string.image_base_342) + movie.poster_path
        Glide
            .with(context)
            .load(imgUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding?.posterImg!!)
        binding.movie = movie
        binding.executePendingBindings()
        if(!movie.release_date.isEmpty())
        binding.yearTxt.setText(getFormattedDate(movie.release_date))

    }

    private fun getFormattedDate(releaseDate: String): String {
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val format2 = SimpleDateFormat("yyyy", Locale.getDefault())
        val dateFormat = format1.parse(releaseDate)
        return format2.format(dateFormat)
    }

}