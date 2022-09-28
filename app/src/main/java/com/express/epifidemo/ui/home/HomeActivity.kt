package com.express.epifidemo.ui.home

import android.app.Service
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.express.epifidemo.R
import com.express.epifidemo.constants.MovieType
import com.express.epifidemo.data.Movie
import com.express.epifidemo.databinding.ActivityHomeBinding
import com.express.epifidemo.helpers.SpacingItemDecoration
import com.express.epifidemo.ui.home.adapters.HomeMovieAdapter
import com.express.epifidemo.viewmodel.HomeMovieViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), HomeMovieAdapter.OnItemClickListener,
    TabLayout.OnTabSelectedListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapterMovies: HomeMovieAdapter
    val viewModel: HomeMovieViewModel by viewModels()
    private lateinit var imm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setUpUi()
        searchQuery()
        fetchRandomData()
        setUpObservers()
    }

    private fun setUpUi() {
        with(binding.rvMovies) {
            layoutManager = GridLayoutManager(this@HomeActivity, 3)
            addItemDecoration(SpacingItemDecoration(50, 3, 0))
            adapterMovies = HomeMovieAdapter(this@HomeActivity, this@HomeActivity)
            adapter = adapterMovies
        }

        with(binding.tabLayout) {
            addTab(newTab().setText(MovieType.Home.name))
            addTab(newTab().setText(MovieType.Movie.name))
            addTab(newTab().setText(MovieType.Series.name))
            addTab(newTab().setText(MovieType.Episode.name))
            addOnTabSelectedListener(this@HomeActivity)
        }

        imm = getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun updateData() {
        if (viewModel.searchQuery.value.isNullOrBlank()) {
            fetchRandomData()
        } else {
            searchQuery()
        }
    }

    private fun searchQuery() {
        lifecycleScope.launch {
            viewModel.searchMoviesUsingQuery().collectLatest {
                adapterMovies.submitData(it)
            }
        }
    }

    private fun fetchRandomData() {
        lifecycleScope.launchWhenResumed {
            viewModel.fetchRandomMovieData().collectLatest {
                adapterMovies.submitData(it)
            }
        }
    }

    private fun setUpObservers() {
        viewModel.searchQuery.observe(this){
            if(it.isNullOrBlank()){
                Log.d("TAG_ST", "null observed")
                fetchRandomData()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_bookmark -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(position: Int, Movie: Movie) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val currentMovieType = MovieType.valueOf(tab?.text.toString())
        viewModel.updateCurrentMovieType(currentMovieType)
        updateData()
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        // do nothing
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        // do nothing
    }
}