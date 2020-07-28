package com.ifreshmart.githubsearch2020.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ifreshmart.githubsearch2020.R
import com.ifreshmart.githubsearch2020.view.detail.fragment.FragmentFollowers
import com.ifreshmart.githubsearch2020.view.detail.fragment.FragmentFollowing

class DetailAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabs = listOf(
        FragmentFollowers(),
        FragmentFollowing()
    )

    private val tabTitle = intArrayOf(
        R.string.tb_followers,
        R.string.tb_following
    )

    override fun getItem(position: Int): Fragment {
        return tabs[position]
    }

    override fun getCount(): Int {
        return tabs.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitle[position])
    }
}