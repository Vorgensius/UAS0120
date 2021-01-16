package com.example.uas0120


import android.app.Activity
import android.content.Context
import com.example.uas0120.Eventsitem
import com.example.uas0120.ServerCall

interface MatchInterface {
    fun isNetworkAvailable(context: Activity): Boolean
    fun getSpinnerData(context: Activity, callback: ServerCall)
    fun getNextMatch(context: Activity, id : String, callback: ServerCall)
    fun isSuccess(response: String): Boolean
    fun parsingData(context: Activity, response: String): ArrayList<Eventsitem>
    fun getFavoritesAll(context: Context) : ArrayList<Eventsitem>
}