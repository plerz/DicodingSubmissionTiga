package com.ifreshmart.githubsearch2020.view.detail

import android.content.ContentValues
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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.ifreshmart.githubsearch2020.R
import com.ifreshmart.githubsearch2020.adapter.DetailAdapter
import com.ifreshmart.githubsearch2020.db.DatabaseContract.FavoriteColumns.Companion.AVATAR_URL
import com.ifreshmart.githubsearch2020.db.DatabaseContract.FavoriteColumns.Companion.LOGIN
import com.ifreshmart.githubsearch2020.db.DatabaseContract.FavoriteColumns.Companion.TYPE
import com.ifreshmart.githubsearch2020.db.FavoriteHelper
import com.ifreshmart.githubsearch2020.model.DataUser
import com.ifreshmart.githubsearch2020.view.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

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
}