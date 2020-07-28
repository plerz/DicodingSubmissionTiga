package com.ifreshmart.githubsearch2020.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ifreshmart.githubsearch2020.R
import com.ifreshmart.githubsearch2020.model.DataUser
import com.ifreshmart.githubsearch2020.view.detail.DetailActivity
import kotlinx.android.synthetic.main.item_git.view.*


class UsersAdapter(private val listDataUserGithub: ArrayList<DataUser>) :
    RecyclerView.Adapter<UsersAdapter.ListDataHolder>() {

    fun setData(items: ArrayList<DataUser>) {
        listDataUserGithub.clear()
        listDataUserGithub.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataUser: DataUser) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataUser.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(avatar)

                id_username.text = itemView.context.getString(R.string.tx_username,dataUser.username)
                id_name.text = dataUser.name
                id_followers.text = itemView.context.getString(R.string.tx_followers, dataUser.followers)
                id_following.text = itemView.context.getString(R.string.tx_following, dataUser.following)
                id_repository.text = itemView.context.getString(R.string.tx_repository, dataUser.repository)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        return ListDataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_git, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listDataUserGithub.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataUserGithub[position])

        val data = listDataUserGithub[position]
        holder.itemView.setOnClickListener {
            val dataUserIntent = DataUser(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following
            )
            val mIntent = Intent(it.context, DetailActivity::class.java)
            mIntent.putExtra(DetailActivity.EXTRA_DETAIL, dataUserIntent)
            it.context.startActivity(mIntent)
        }
    }


}