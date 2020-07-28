package com.ifreshmart.githubsearch2020.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ifreshmart.githubsearch2020.R
import com.ifreshmart.githubsearch2020.model.DataFollowing
import kotlinx.android.synthetic.main.item_git.view.*

class FollowingAdapter(private val listDataFollowing: ArrayList<DataFollowing>) :
    RecyclerView.Adapter<FollowingAdapter.ListDataHolder>() {

    fun setData(item: ArrayList<DataFollowing>) {
        listDataFollowing.clear()
        listDataFollowing.addAll(item)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataFollowing: DataFollowing) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataFollowing.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(avatar)

                id_username.text = itemView.context.getString(R.string.tx_username, dataFollowing.username)
                id_name.text = dataFollowing.name
                id_followers.text = itemView.context.getString(R.string.tx_followers, dataFollowing.followers)
                id_following.text = itemView.context.getString(R.string.tx_followers, dataFollowing.following)
                id_repository.text = itemView.context.getString(R.string.tx_repository, dataFollowing.repository)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListDataHolder {
        return ListDataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_git, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listDataFollowing.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataFollowing[position])
    }
}