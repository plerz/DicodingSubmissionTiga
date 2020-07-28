package com.ifreshmart.githubsearch2020.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.ifreshmart.githubsearch2020"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val _ID = "_id"
            const val LOGIN = "login"
            const val AVATAR_URL = "avatar_url"
            const val TYPE = "type"

            // URI content : com.ifreshmart.githubsearch2020
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}