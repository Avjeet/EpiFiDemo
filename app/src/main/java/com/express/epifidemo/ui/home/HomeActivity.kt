package com.express.epifidemo.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.recyclerview.widget.GridLayoutManager
import com.express.epifidemo.R
import com.express.epifidemo.data.Movie
import com.express.epifidemo.databinding.ActivityHomeBinding
import com.express.epifidemo.helpers.SpacingItemDecoration
import com.express.epifidemo.ui.home.adapters.HomeMovieAdapter

class HomeActivity : AppCompatActivity(), HomeMovieAdapter.OnItemClickListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapterMovies: HomeMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        setSupportActionBar(findViewById(R.id.toolbar))

        setUpUi()
        inflateData()
    }

    private fun setUpUi() {
        with(binding.rvMovies) {
            layoutManager = GridLayoutManager(this@HomeActivity, 3)
            addItemDecoration(SpacingItemDecoration(50, 3, 0))
            adapterMovies = HomeMovieAdapter(this@HomeActivity, this@HomeActivity)
            adapter = adapterMovies
        }
    }

    private fun inflateData() {
        adapterMovies.submitList(Movie.getTestData())
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
}