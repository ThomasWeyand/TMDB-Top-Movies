package br.com.thomas.tmdbtop.presentation.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.thomas.tmdbtop.databinding.SearchItemBinding

class SearchViewHolder (view: View, context : Context) : RecyclerView.ViewHolder(view){

    val binding: SearchItemBinding? = DataBindingUtil.bind(view)
}