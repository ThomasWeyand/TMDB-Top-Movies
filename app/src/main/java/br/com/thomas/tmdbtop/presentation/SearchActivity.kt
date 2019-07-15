package br.com.thomas.tmdbtop.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import br.com.thomas.tmdbtop.R
import br.com.thomas.tmdbtop.databinding.SearchActivityLayoutBinding
import br.com.thomas.tmdbtop.domain.Resource
import br.com.thomas.tmdbtop.domain.models.Movie
import br.com.thomas.tmdbtop.domain.network.Status
import br.com.thomas.tmdbtop.presentation.adapters.RecyclerViewPaginator
import br.com.thomas.tmdbtop.presentation.adapters.SearchAdapter
import dagger.android.AndroidInjection
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), ListHomeInterface{

    private lateinit var mBinding : SearchActivityLayoutBinding
    private lateinit var mViewModel : SearchViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mSearchAdapter : SearchAdapter
    private lateinit var mSearchQuery : String
    private lateinit var mPaginator : RecyclerViewPaginator

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.search_activity_layout)
        mBinding.setLifecycleOwner(this)
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(SearchViewModel::class.java)
        setUpRecycle()
        setUpThisObservables()
        if(intent.extras?.getString("query") != null) {
            mSearchQuery = intent.extras?.getString("query").toString()
            loadSearch(1)
            setUpToolbar(mSearchQuery)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else ->
                return super.onOptionsItemSelected(item);
        }
    }

    private fun setUpToolbar(title : String) {
        val toolbar = mBinding.searchToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.setTitle(title)
    }

    private fun setUpRecycle() {
        val movieList : ArrayList<Movie> = ArrayList()
        mSearchAdapter = SearchAdapter(this,movieList,this)
        val linearLayout = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        mBinding.searchRecycle.layoutManager = linearLayout
        mBinding.searchRecycle.adapter = mSearchAdapter
        mPaginator = RecyclerViewPaginator(
            recyclerView = mBinding.searchRecycle,
            isLoading = { mViewModel.getMovieListValues().value?.status == Status.LOADING },
            loadMore = { loadSearch(it) },
            onLast = { mViewModel.getMovieListValues().value?.onLastPage!! })
        mPaginator.currentPage = 1
    }

    private fun setUpThisObservables() {
        mViewModel.getMovieListValues().observe(this, Observer<Resource<List<Movie>>>{value ->
            value?.let {
                if(value.status == Status.LOADING){
                    mBinding.loadingSpinner.visibility == View.VISIBLE
                }
                else if(value.status == Status.SUCCESS) {
                    mBinding.loadingSpinner.visibility = View.GONE
                    if(value.data != null && value.data.size > 0) {
                        mSearchAdapter.swapData(value.data)
                    }else {
                        if(mSearchAdapter.itemCount == 0) {
                            mBinding.errorTxt.setText(resources.getString(R.string.not_found))
                        }else{}
                    }
                }else{
                    mBinding.loadingSpinner.visibility = View.GONE
                    if(mSearchAdapter.itemCount == 0) {
                        mBinding.errorTxt.setText(resources.getString(R.string.error_fetching))
                    }else{}
                }
            }
        })
    }

    private fun loadSearch(page: Int) {
        mViewModel.postMoviePage(page,mSearchQuery)
    }

    override fun movieSelected(movie: Movie) {
        val intent = Intent(this,MovieDetailsActivity::class.java)
        intent.putExtra("movieExtra",movie)
        startActivity(intent)
    }
}