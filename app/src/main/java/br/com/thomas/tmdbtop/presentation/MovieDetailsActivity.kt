package br.com.thomas.tmdbtop.presentation

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.thomas.tmdbtop.R
import br.com.thomas.tmdbtop.databinding.MovieDetailLayoutBinding
import br.com.thomas.tmdbtop.domain.models.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.movie_detail_layout.*
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var mBinding : MovieDetailLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.movie_detail_layout)
        mBinding.setLifecycleOwner(this)

        if(intent.extras?.getParcelable<Movie>("movieExtra") != null) {
            val movie = intent.extras.getParcelable<Movie>("movieExtra")
            bindViewsMethod(movie)
        }
        setUpToolbar()

    }

    private fun setUpToolbar() {
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitle("")
        mBinding.backArrow.setOnClickListener{
            onBackPressed()
        }
    }

    private fun bindViewsMethod(movie: Movie) {
        drawThumbs(movie)
        val appBarLayout = mBinding.appBar
        val toolbar = mBinding.toolbar
        val collaBar = mBinding.toolbarLayout
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener{
            override fun onOffsetChanged(p0: AppBarLayout?, verticalOffset: Int) {
                if (appBarLayout.totalScrollRange + verticalOffset == 0) {
                    collaBar.setTitle(movie.title)
                    toolbar.setVisibility(View.VISIBLE)
                } else {
                    collaBar.setTitle("")
                    toolbar.setVisibility(View.INVISIBLE)
                }
            }

        })

        mBinding.movie = movie
        mBinding.executePendingBindings()
        mBinding.contentMovieDetails.textViewRatingMovieDetail.setText(String.format("%.1f",movie.vote_average))
        mBinding.contentMovieDetails.textViewOverviewMovieDetail.setText(movie.overview)
        mBinding.contentMovieDetails.launchDate.setText(getFormattedDate(movie.release_date))
    }

    private fun getFormattedDate(releaseDate: String): String {
        val format1 = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())
        val format2 = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateFormat = format1.parse(releaseDate)
        return format2.format(dateFormat)
    }

    private fun drawThumbs(movie: Movie) {
        val posterWidth = (resources.displayMetrics.widthPixels * 0.25).toInt()
        val posterHeight = (posterWidth / 0.66).toInt()
        val backDropWidth = resources.displayMetrics.widthPixels
        val backDropHeight = (backDropWidth / 1.77).toInt()

        mBinding.layoutToolbarMovie.getLayoutParams().height = backDropHeight + (posterHeight * 0.9).toInt()
        val posterimg = mBinding.imageViewPoster
        posterimg.getLayoutParams().height = posterHeight
        posterimg.layoutParams.width = posterWidth
        val backDropImg = mBinding.imageViewBackdrop
        backDropImg.layoutParams.height = backDropHeight


        val posterPath = resources.getString(R.string.image_base_342) + movie.poster_path
        val backDropPath = resources.getString(R.string.base_backdrop_path) + movie.backdrop_path
        Glide.with(this)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .load(backDropPath)
            .into(backDropImg)
        Glide.with(this)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .load(posterPath)
            .into(posterimg)
    }
}