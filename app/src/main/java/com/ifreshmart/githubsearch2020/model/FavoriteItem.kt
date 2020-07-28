package com.ifreshmart.githubsearch2020.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteItem(
    var id: Int = 0,
    var login: String? = null,
    var avatar: String? = null,
    var type: String? = null
) : Parcelable