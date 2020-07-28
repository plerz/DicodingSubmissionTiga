package com.ifreshmart.githubsearch2020.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ifreshmart.githubsearch2020.R
import com.ifreshmart.githubsearch2020.model.FavoriteItem
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteAdapter(private val listener: (FavoriteItem) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listFavorite = ArrayList<FavoriteItem>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position], listener)
    }

    override fun getItemCount(): Int = this.listFavorite.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favoriteItem: FavoriteItem, listener: (FavoriteItem) -> Unit) {
            with(itemView) {
                tvLogin.text = favoriteItem.login
                tvType.text = favoriteItem.type
                Glide.with(context)
                    .load(favoriteItem.avatar)
                    .apply(RequestOptions().override(56, 56))
                    .into(imgUser)
                setOnClickListener { listener(favoriteItem) }
            }
        }
    }
}