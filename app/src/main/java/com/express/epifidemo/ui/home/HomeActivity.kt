package com.express.epifidemo.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), HomeMovieAdapter.OnItemClickListener,
    TabLayout.OnTabSelectedListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapterMovies: HomeMovieAdapter
    val viewModel: HomeMovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        setSupportActionBar(findViewById(R.id.toolbar))

        setUpUi()
        inflateData(MovieType.Home)
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
    }

    private fun inflateData(type: MovieType) {
        lifecycleScope.launchWhenResumed {
            viewModel.fetchRandomMovieData(type = type.value).collectLatest {
                adapterMovies.submitData(it)
//                when (it) {
//                    is Result.Loading -> {
//                        Toast.makeText(this@HomeActivity, "Loading", Toast.LENGTH_LONG).show()
//                    }
//
//                    is Result.Success -> {
//                        adapterMovies.submitList(it.data)
//                    }
//
//                    is Result.Failure -> {
//                        Toast.makeText(this@HomeActivity, it.message, Toast.LENGTH_LONG).show()
//                    }
//                }
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
        inflateData(MovieType.valueOf(tab?.text.toString()))
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        // do nothing
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        // do nothing
    }
}