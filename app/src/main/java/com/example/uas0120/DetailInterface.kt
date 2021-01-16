package com.example.uas0120

import android.app.Activity
import android.content.Context

interface DetailInterface {
    fun isNetworkAvailable(context: Activity): Boolean
    fun geDetailTeam(context: Activity, id : String, callback: ServerCall)
    fun isSuccess(response: String): Boolean
    fun parsingData(context: Activity, response: String): ArrayList<Eventsitem>
    fun addFavorites(context: Context, data: Eventsitem)
    fun removeFavorites(context: Context, data: Eventsitem)
    fun isFavorite(context: Context, data: Eventsitem): Boolean
}