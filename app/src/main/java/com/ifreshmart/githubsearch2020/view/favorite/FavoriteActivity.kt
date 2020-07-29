package com.ifreshmart.githubsearch2020.view.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ifreshmart.githubsearch2020.R
import com.ifreshmart.githubsearch2020.adapter.FavoriteAdapter
import com.ifreshmart.githubsearch2020.db.FavoriteHelper
import com.ifreshmart.githubsearch2020.db.MappingHelper
import com.ifreshmart.githubsearch2020.model.DataFavorite
import com.ifreshmart.githubsearch2020.view.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteHelper: FavoriteHelper

    companion object {
        const val EXTRA_LOGIN = "extra_login"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
        const val EXTRA_TYPE = "extra_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.setTitle(R.string.tx_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showListFavoriteUser()

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        /**
         *  Cek jika savedInstaceState null makan akan melakukan proses asynctask nya
         *  jika tidak,akan mengambil arraylist nya dari yang sudah di simpan
         **/
        if (savedInstanceState == null) {
            loadFavoriteUserAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<DataFavorite>(EXTRA_LOGIN)
            if (list != null) {
                adapter.listFavoriteUser = list
            }
        }
    }

    private fun showListFavoriteUser() {
        rv_favorite.layoutManager = LinearLayoutManager(this)
        rv_favorite.setHasFixedSize(true)

        adapter = FavoriteAdapter{
            val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
            intent.apply {
                putExtra(EXTRA_LOGIN, it.login)
                putExtra(EXTRA_AVATAR_URL, it.avatar_url)
                putExtra(EXTRA_TYPE, it.type)
            }
            startActivity(intent)
        }

        rv_favorite.adapter = adapter
    }

    private fun loadFavoriteUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            val deferredFavorites = async(Dispatchers.IO) {
                val cursor = favoriteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progressBar.visibility = View.INVISIBLE
            val favorites = deferredFavorites.await()
            if (favorites.size > 0) {
                adapter.listFavoriteUser = favorites
            } else {
                adapter.listFavoriteUser = ArrayList()
                Snackbar.make(rv_favorite,
                    "Tidak ada data saat ini", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putParcelableArrayList(EXTRA_LOGIN, adapter.listFavoriteUser)
            putParcelableArrayList(EXTRA_AVATAR_URL, adapter.listFavoriteUser)
            putParcelableArrayList(EXTRA_TYPE, adapter.listFavoriteUser)
        }
    }

    override fun onResume() {
        super.onResume()
        showListFavoriteUser()
        loadFavoriteUserAsync()
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }

    // fungsi back button support action bar
    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

}