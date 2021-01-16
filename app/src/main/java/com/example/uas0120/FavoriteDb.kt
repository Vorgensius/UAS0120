package com.example.uas0120

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.uas0120.Eventsitem
import org.jetbrains.anko.db.*

class FavoritesDb (context: Context) : ManagedSQLiteOpenHelper(context, "Favorites.db", null, 1) {
    companion object {
        private var instance: FavoritesDb? = null
        @Synchronized
        fun getInstance(context: Context): FavoritesDb {
            if (instance == null) {
                instance = FavoritesDb (context.applicationContext)
            }
            return instance as FavoritesDb
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            Eventsitem.TABLE_FAVORITES, true,
            Eventsitem.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Eventsitem.ID_EVENT to TEXT,
            Eventsitem.DATE to TEXT,
            Eventsitem.STR_SPORT to TEXT,

            // home team
            Eventsitem.HOME_ID to TEXT,
            Eventsitem.HOME_TEAM to TEXT,
            Eventsitem.HOME_SCORE to TEXT,
            Eventsitem.HOME_FORMATION to TEXT,
            Eventsitem.HOME_GOAL_DETAILS to TEXT,
            Eventsitem.HOME_SHOTS to TEXT,
            Eventsitem.HOME_LINEUP_GOALKEEPER to TEXT,
            Eventsitem.HOME_LINEUP_DEFENSE to TEXT,
            Eventsitem.HOME_LINEUP_MIDFIELD to TEXT,
            Eventsitem.HOME_LINEUP_FORWARD to TEXT,
            Eventsitem.HOME_LINEUP_SUBSTITUTES to TEXT,

            // away team
            Eventsitem.AWAY_ID to TEXT,
            Eventsitem.AWAY_TEAM to TEXT,
            Eventsitem.AWAY_SCORE to TEXT,
            Eventsitem.AWAY_FORMATION to TEXT,
            Eventsitem.AWAY_GOAL_DETAILS to TEXT,
            Eventsitem.AWAY_SHOTS to TEXT,
            Eventsitem.AWAY_LINEUP_GOALKEEPER to TEXT,
            Eventsitem.AWAY_LINEUP_DEFENSE to TEXT,
            Eventsitem.AWAY_LINEUP_MIDFIELD to TEXT,
            Eventsitem.AWAY_LINEUP_FORWARD to TEXT,
            Eventsitem.AWAY_LINEUP_SUBSTITUTES to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Eventsitem.TABLE_FAVORITES, true)
    }
}

val Context.database: FavoritesDb
    get() = FavoritesDb.getInstance(applicationContext)