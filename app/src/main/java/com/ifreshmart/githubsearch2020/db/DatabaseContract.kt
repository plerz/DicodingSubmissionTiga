package com.ifreshmart.githubsearch2020.db

import android.net.Uri
import android.provider.BaseColumns

internal class DatabaseContract {

    internal class FavoriteUserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val _ID = "_id"
            const val LOGIN = "login"
            const val AVATAR_URL = "avatar_url"
            const val TYPE = "type"
        }
    }
}