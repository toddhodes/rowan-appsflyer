package com.example.imagesearchapp.ui.main.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashPhoto (
    val id : String,
    val desc : String?,
    val urls : UnsplashImageUrls,
    val user : UnsplashImageUser
) : Parcelable  {

    @Parcelize
    data class UnsplashImageUrls(
        val raw : String,
        val full : String,
        val regular : String,
        val small : String,
        val thumb : String,
    ) : Parcelable

    @Parcelize
    data class UnsplashImageUser (
        val name : String,
        val username : String
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}
