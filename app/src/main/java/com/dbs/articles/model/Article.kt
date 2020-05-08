package com.dbs.articles.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    var id: Int,
    var title: String,
    var last_update: Long,
    var short_description: String,
    var avatar: String
) : Parcelable