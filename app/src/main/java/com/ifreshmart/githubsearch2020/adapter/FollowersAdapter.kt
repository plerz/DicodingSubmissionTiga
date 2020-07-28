package com.ifreshmart.githubsearch2020.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ifreshmart.githubsearch2020.R
import com.ifreshmart.githubsearch2020.model.DataFollowers
import kotlinx.android.synthetic.main.item_git.view.*

class FollowersAdapter(private val listDataFollower: ArrayList<DataFollowers>) :
    RecyclerView.Adapter<FollowersAdapter.ListDataHolder>() {

    fun setData(items: ArrayList<DataFollowers>) {
        listDataFollower.clear()
        listDataFollower.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataFollowers: DataFollowers) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataFollowers.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(avatar)

                id_username.text = itemView.context.getString(R.string.tx_username, dataFollowers.username)
                id_name.text = dataFollowers.name
                id_followers.text = itemView.context.getString(R.string.tx_followers, dataFollowers.followers)
                id_following.text = itemView.context.getString(R.string.tx_following, dataFollowers.following)
                id_repository.text = itemView.context.getString(R.string.tx_repository, dataFollowers.repository)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        return ListDataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_git, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listDataFollower.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataFollower[position])
    }
}