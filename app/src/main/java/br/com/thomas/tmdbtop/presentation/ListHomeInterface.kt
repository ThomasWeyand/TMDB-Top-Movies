package br.com.thomas.tmdbtop.presentation

import br.com.thomas.tmdbtop.domain.models.Movie

interface ListHomeInterface {

    fun movieSelected(movie: Movie)

}