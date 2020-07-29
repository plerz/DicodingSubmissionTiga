package com.ifreshmart.githubsearch2020.db

import android.database.Cursor
import com.ifreshmart.githubsearch2020.model.DataFavorite
import java.util.ArrayList

object MappingHelper {

    fun mapCursorToArrayList(favoriteUserCursor: Cursor?): ArrayList<DataFavorite> {
        val favoriteUserItemsList = ArrayList<DataFavorite>()

        favoriteUserCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns._ID))
                val login = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.LOGIN))
                val avatarUrl = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.AVATAR_URL))
                val type = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.TYPE))
                favoriteUserItemsList.add(DataFavorite(id, login, avatarUrl, type))
            }
        }
        return favoriteUserItemsList
    }
}