package com.ifreshmart.githubsearch2020.db

import android.database.Cursor
import com.ifreshmart.githubsearch2020.db.DatabaseContract.FavoriteColumns.Companion.AVATAR_URL
import com.ifreshmart.githubsearch2020.db.DatabaseContract.FavoriteColumns.Companion.LOGIN
import com.ifreshmart.githubsearch2020.db.DatabaseContract.FavoriteColumns.Companion._ID
import com.ifreshmart.githubsearch2020.model.FavoriteItem

object MappingHelper {

    fun mapCursorToArrayList(favoriteCursor: Cursor?): ArrayList<FavoriteItem> {
        val favoriteItemList = ArrayList<FavoriteItem>()

        favoriteCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val login = getString(getColumnIndexOrThrow(LOGIN))
                val avatarUrl = getString(getColumnIndexOrThrow(AVATAR_URL))
                favoriteItemList.add(FavoriteItem(id, login, avatarUrl))
            }
        }
        return favoriteItemList
    }
}