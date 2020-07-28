package com.ifreshmart.githubsearch2020.view.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifreshmart.githubsearch2020.R
import com.ifreshmart.githubsearch2020.adapter.FollowersAdapter
import com.ifreshmart.githubsearch2020.model.DataFollowers
import com.ifreshmart.githubsearch2020.model.DataUser
import com.ifreshmart.githubsearch2020.viewModel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_follower.*

class FragmentFollowers : Fragment() {

    companion object {
        val TAG = FragmentFollowers::class.java.simpleName
        const val EXTRA_DETAIL = "extra_detail"
    }

    private val listData: ArrayList<DataFollowers> = ArrayList()
    private lateinit var adapter: FollowersAdapter
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter =
            FollowersAdapter(listData)
        followersViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)

        val dataUser = activity!!.intent.getParcelableExtra(EXTRA_DETAIL) as DataUser
        config()

        followersViewModel.getDataGit(activity!!.applicationContext, dataUser.username.toString())
        showLoading(true)

        followersViewModel.getListFollower().observe(activity!!, Observer { listFollower ->
            if (listFollower != null) {
                adapter.setData(listFollower)
                showLoading(false)
            }
        })
    }

    private fun config() {
        recViewFollowers.layoutManager = LinearLayoutManager(activity)
        recViewFollowers.setHasFixedSize(true)
        recViewFollowers.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            loadingFollowers.visibility = View.VISIBLE
        } else {
            loadingFollowers.visibility = View.INVISIBLE
        }
    }

}