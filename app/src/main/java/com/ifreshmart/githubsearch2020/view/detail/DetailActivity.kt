package com.ifreshmart.githubsearch2020.view.detail

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.ifreshmart.githubsearch2020.R
import com.ifreshmart.githubsearch2020.adapter.DetailAdapter
import com.ifreshmart.githubsearch2020.db.DatabaseContract
import com.ifreshmart.githubsearch2020.db.FavoriteHelper
import com.ifreshmart.githubsearch2020.model.DataUser
import com.ifreshmart.githubsearch2020.view.favorite.FavoriteActivity.Companion.EXTRA_LOGIN
import com.ifreshmart.githubsearch2020.view.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
        const val EXTRA_TYPE = "extra_type"
    }
    // private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteHelper: FavoriteHelper
    private lateinit var username: String
    private lateinit var avatarUrl: String
    private lateinit var type: String
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false


    //fun onFavoriteClicked(user: DataUser,context: Context){

    //}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //Orientation
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            id_viewpages.layoutParams.height = resources.getDimension(R.dimen.height).toInt()
        } else {
            id_viewpages.layoutParams.height = resources.getDimension(R.dimen.height001).toInt()
        }

        if (supportActionBar != null) {
            supportActionBar?.title = "Detail User"
        }

        setData()
        viewPagerConfig()

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()


        favoriteState()
    }

    private fun viewPagerConfig() {
        val viewPagerDetail =
            DetailAdapter(
                this,
                supportFragmentManager
            )
        id_viewpages.adapter = viewPagerDetail
        id_tab.setupWithViewPager(id_viewpages)
        supportActionBar?.elevation = 0f
    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra(EXTRA_DETAIL) as DataUser
        Glide.with(this)
            .load(dataUser.avatar)
            .apply(RequestOptions().override(150, 130))
            .into(avatars)

        id_username.text = getString(R.string.tx_username, dataUser.username)
        id_name.text = dataUser.name
        id_company.text = dataUser.company
        id_location.text = dataUser.location
        id_following.text = dataUser.following
        id_followers.text = dataUser.followers
        id_repository.text = dataUser.repository
    }

    //Start

    // fungsi untuk menampilkan tabs


    // fungsi untuk mengecek status apakah favorite user sudah masuk ke dalam database atau belum
    private fun favoriteState(){
        username = intent?.getStringExtra(EXTRA_DETAIL).toString()
        val result = favoriteHelper.queryByLogin(username)
        val favorite = (1 .. result.count).map {
            result.apply {
                moveToNext()
                getInt(result.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.LOGIN))
            }
        }
        if (favorite.isNotEmpty()) isFavorite = true
    }

    // fungsi untuk menambahkan favorite user ke database sqlite
    private fun addFavoriteUser() {
        try {
            username = intent?.getStringExtra(EXTRA_DETAIL).toString()
            avatarUrl = intent?.getStringExtra(EXTRA_AVATAR_URL).toString()
            type = intent?.getStringExtra(EXTRA_TYPE).toString()

            val values = ContentValues().apply {
                put(DatabaseContract.FavoriteUserColumns.LOGIN, username)
                put(DatabaseContract.FavoriteUserColumns.AVATAR_URL, avatarUrl)
                put(DatabaseContract.FavoriteUserColumns.TYPE, type)
            }
            favoriteHelper.insert(values)

            showSnackbarMessage("Added to favorite")
            Log.d("INSERT VALUES ::::: ", values.toString())
        } catch (e: SQLiteConstraintException) {
            showSnackbarMessage(""+e.localizedMessage)
        }
    }

    // fungsi untuk menghapus data favorite user dari database sqlite
    private fun removeFavoriteUser(){
        try {
            username = intent?.getStringExtra(EXTRA_DETAIL).toString()
            val result = favoriteHelper.deleteByLogin(username)

            showSnackbarMessage("Removed to favorite")
            Log.d("REMOVE VALUES ::::: ", result.toString())
        } catch (e: SQLiteConstraintException){
            showSnackbarMessage(""+e.localizedMessage)
        }
    }

    // fungsi untuk mengatur ikon pada tombol Favorite
    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.add_favorite -> {
                if (isFavorite) removeFavoriteUser() else addFavoriteUser()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // fungsi back button support action bar
    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    // Tampilkan snackbar
    private fun showSnackbarMessage(message: String) {
    }
}