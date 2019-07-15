package br.com.thomas.tmdbtop.presentation.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.thomas.tmdbtop.R
import br.com.thomas.tmdbtop.domain.models.Movie
import br.com.thomas.tmdbtop.domain.models.SearchModel
import java.util.ArrayList

class SearchAdapter(val context : Context, val searchList : ArrayList<Movie>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item,parent,false),context)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {

    }
}