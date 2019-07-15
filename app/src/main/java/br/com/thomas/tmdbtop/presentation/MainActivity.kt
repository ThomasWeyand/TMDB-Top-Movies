package br.com.thomas.tmdbtop.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.widget.Toast
import br.com.thomas.tmdbtop.R
import br.com.thomas.tmdbtop.databinding.ActivityMainBinding
import br.com.thomas.tmdbtop.domain.Resource
import br.com.thomas.tmdbtop.domain.models.Movie
import br.com.thomas.tmdbtop.domain.network.Status
import br.com.thomas.tmdbtop.presentation.adapters.RecyclerViewPaginator
import br.com.thomas.tmdbtop.presentation.adapters.TopListAdapter
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ListHomeInterface {

    override fun movieSelected(movie: Movie) {
        val intent = Intent(this,MovieDetailsActivity::class.java)
        intent.putExtra("movieExtra",movie)
        startActivity(intent)
    }

    lateinit var mBinding : ActivityMainBinding
    lateinit var mMovieAdapter : TopListAdapter
    lateinit var mSnackMsg : Snackbar

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mViewModel : MainActivityViewModel
    lateinit var mPaginator : RecyclerViewPaginator

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mBinding.setLifecycleOwner(this)

        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(MainActivityViewModel::class.java)

        setUpToolbar()
        setUpRecycle()
        setUpObservables()
        loadMore(1)
    }

    private fun setUpToolbar() {
        val toolbar = mBinding.mainToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(resources.getString(R.string.toolbar_title))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main_menu,menu)
        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                intent.putExtra("query", query)
                startActivity(intent)
                menuItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    fun manageSnackMsg(msg : String){
        mSnackMsg.dismiss()
        mSnackMsg = Snackbar.make(mBinding.mainContainer,msg,Snackbar.LENGTH_LONG)
        mSnackMsg.show()
    }

    private fun setUpObservables() {
        mViewModel.getMovieListValues().observe(this, Observer<Resource<List<Movie>>> { value ->
            value?.let {
                if(value.status == Status.LOADING){
                    mBinding.loadingSpinner.visibility == View.VISIBLE
                }
                else if(value.status == Status.SUCCESS) {
                    mBinding.loadingSpinner.visibility = View.GONE
                    if(value.data != null && value.data.size > 0) {
                        mMovieAdapter.swapData(value.data)
                    }else {
                        if(mMovieAdapter.itemCount == 0) {
                            mBinding.errorTxt.setText(resources.getString(R.string.not_found))
                        }else{
                            manageSnackMsg(resources.getString(R.string.not_found))
                        }
                    }
                }else{
                    mBinding.loadingSpinner.visibility = View.GONE
                    if(mMovieAdapter.itemCount == 0) {
                        mBinding.errorTxt.setText(resources.getString(R.string.error_fetching))
                    }else{
                        manageSnackMsg(resources.getString(R.string.error_fetching))
                    }
                }
            }
        })
    }

    private fun setUpRecycle() {
        val moviesList : ArrayList<Movie> = ArrayList()
        mMovieAdapter = TopListAdapter(this, moviesList,this)
        val gridLayout = GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false)
        mBinding.recyclerViewTop.layoutManager = gridLayout
        mBinding.recyclerViewTop.adapter = mMovieAdapter
        mPaginator = RecyclerViewPaginator(
            recyclerView = mBinding.recyclerViewTop,
            isLoading = { mViewModel.getMovieListValues().value?.status == Status.LOADING },
            loadMore = { loadMore(it) },
            onLast = { mViewModel.getMovieListValues().value?.onLastPage!! }
        )
        mPaginator.currentPage = 1
    }

    private fun loadMore(page: Int) {
        mViewModel.postMoviePage(page)
    }

}
