package com.ifreshmart.githubsearch2020.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ifreshmart.githubsearch2020.R
import com.ifreshmart.githubsearch2020.model.DataFavorite
import kotlinx.android.synthetic.main.item_favorite.view.*
import java.util.ArrayList

class FavoriteAdapter(private val listener: (DataFavorite) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listFavoriteUser = ArrayList<DataFavorite>()
        set(listFavoriteUser) {
            if (listFavoriteUser.size > 0) {
                this.listFavoriteUser.clear()
            }
            this.listFavoriteUser.addAll(listFavoriteUser)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavoriteUser[position], listener)
    }

    override fun getItemCount(): Int = this.listFavoriteUser.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataFavorite: DataFavorite, listener: (DataFavorite) -> Unit) {
            with(itemView){
                textLogin.text = dataFavorite.login
                textType.text = dataFavorite.type
                Glide.with(context)
                    .load(dataFavorite.avatar_url)
                    .apply(RequestOptions()
                        .override(56,56)
                        .placeholder(R.drawable.ic_account_circle)
                        .error(R.drawable.ic_account_circle)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH))
                    .into(imageAvatar)
                setOnClickListener { listener(dataFavorite) }
            }
        }
    }
}