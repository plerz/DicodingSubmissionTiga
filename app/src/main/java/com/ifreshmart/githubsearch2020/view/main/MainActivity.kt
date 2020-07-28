package com.ifreshmart.githubsearch2020.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifreshmart.githubsearch2020.view.favorite.FavoriteActivity
import com.ifreshmart.githubsearch2020.R
import com.ifreshmart.githubsearch2020.adapter.UsersAdapter
import com.ifreshmart.githubsearch2020.model.DataUser
import com.ifreshmart.githubsearch2020.view.setting.SettingActivity
import com.ifreshmart.githubsearch2020.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listDataUser: ArrayList<DataUser> = ArrayList()
    private lateinit var listAdapter: UsersAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAdapter =
            UsersAdapter(listDataUser)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        searchData()
        viewConfig()
        runGetDataGit()
        configMainViewModel(listAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.favoriteUser) {
            val mIntent = Intent(this, FavoriteActivity::class.java)
            startActivity(mIntent)
        }

        if (item.itemId == R.id.reminder_setting) {
            val mIntent = Intent(this, SettingActivity::class.java)
            startActivity(mIntent)
        }
        if (item.itemId == R.id.change_language) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun viewConfig() {
        recView.layoutManager = LinearLayoutManager(this)
        recView.setHasFixedSize(true)

        listAdapter.notifyDataSetChanged()
        recView.adapter = listAdapter
    }

    private fun runGetDataGit() {
        mainViewModel.getDataGit(applicationContext)
        showLoading(true)
    }

    private fun configMainViewModel(adapter: UsersAdapter) {
        mainViewModel.getListUsers().observe(this, Observer { listUsers ->
            if (listUsers != null) {
                adapter.setData(listUsers)
                showLoading(false)
            }
        })
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            loadingProgress.visibility = View.VISIBLE
        } else {
            loadingProgress.visibility = View.INVISIBLE
        }
    }

    private fun searchData() {
        id_search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    listDataUser.clear()
                    viewConfig()
                    mainViewModel.getDataGitSearch(query, applicationContext)
                    showLoading(true)
                    configMainViewModel(listAdapter)
                } else {
                    return true
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
    }

}