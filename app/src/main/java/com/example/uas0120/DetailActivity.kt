package com.example.uas0120

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.example.uas0120.R
import com.example.uas0120.Eventsitem
import com.example.uas0120.ServerCall
import com.example.uas0120.getLongDate
import com.example.uas0120.R.id.mn_favorites
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.bar.*
import org.jetbrains.anko.ctx
import org.json.JSONObject

const val INTENT_DETAIL = "INTENT_DETAIL"

class DetailActivity : AppCompatActivity() {


    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder
    private val channelId = "12345"
    private val description = "Berhasil menambahkan"
    private var badgeHome: String? = ""
    private var badgeAway: String? = ""
    private var idHome: String? = ""
    private var idAway: String? = ""
    private var idEvent = ""
    private var idTeam = arrayOf<String>()
    private var menuItem: String = ""
    private val presenter = DetailPresenter()
    private var menuFavorites: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var data: Eventsitem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        implementPutExtra()
        setDetail()

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager


    }

    private fun initLayout() {
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar_main)
        menuItem = intent.getStringExtra(getString(R.string.menuItem)).toString()
        data = intent.getParcelableExtra(INTENT_DETAIL)!!
        if (menuItem.toInt() == 1) {
            detailNoResult.visibility = View.INVISIBLE
            layout_score.visibility = View.VISIBLE
            layout_match.visibility = View.VISIBLE
        } else if (menuItem.toInt() == 2) {
            layout_score.visibility = View.INVISIBLE
            layout_match.visibility = View.INVISIBLE
            detailNoResult.visibility = View.VISIBLE
        } else if (menuItem.toInt() == 3) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuFavorites = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24)
        } else {

            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun implementPutExtra() {
        idEvent = data.idEvent.toString()
        dateDetail.text = getLongDate(data.dateEvent)
//        home
        idHome = data.idHomeTeam
        ID_HOME_SCORE_DETAIL.text = data.intHomeScore
        name_home.text = data.strHomeTeam
        if (data.strHomeGoalDetails.equals("null")) {
            GOAL_HOME_SCORE.text = ""
        } else {
            GOAL_HOME_SCORE.text = data.strHomeGoalDetails
        }
        if (data.intHomeShots.equals("null")) {
            SHOTS_HOME_SCORE.text = ""
        } else {
            SHOTS_HOME_SCORE.text = data.intHomeShots
        }
        if (data.strHomeLineupGoalkeeper.equals("null")) {
            GK_HOME.text = ""
        } else {
            GK_HOME.text = data.strHomeLineupGoalkeeper
        }
        if (data.strHomeLineupDefense.equals("null")) {
            DEF_HOME.text = ""
        } else {
            DEF_HOME.text = data.strHomeLineupDefense
        }
        if (data.strHomeLineupMidfield.equals("null")) {
            MID_HOME.text = ""
        } else {
            MID_HOME.text = data.strHomeLineupMidfield
        }
        if (data.strHomeLineupForward.equals("null")) {
            FW_HOME.text = ""
        } else {
            FW_HOME.text = data.strHomeLineupForward
        }
        if (data.strHomeLineupSubstitutes.equals("null")) {
            SUB_HOME.text = ""
        } else {
            SUB_HOME.text = data.strHomeLineupSubstitutes
        }
//        away
        idAway = data.idAwayTeam
        ID_AWAY_SCORE_DETAIL.text = data.intAwayScore
        name_away.text = data.strAwayTeam
        if (data.strAwayGoalDetails.equals("null")) {
            GOAL_AWAY_SCORE.text = ""
        } else {
            GOAL_AWAY_SCORE.text = data.strAwayGoalDetails
        }
        if (data.intAwayShots.equals("null")) {
            SHOTS_AWAY_SCORE.text = ""
        } else {
            SHOTS_AWAY_SCORE.text = data.intAwayShots
        }
        if (data.strAwayLineupGoalkeeper.equals("null")) {
            GK_AWAY.text = ""
        } else {
            GK_AWAY.text = data.strAwayLineupGoalkeeper
        }
        if (data.strAwayLineupDefense.equals("null")) {
            DEF_AWAY.text = ""
        } else {
            DEF_AWAY.text = data.strAwayLineupDefense
        }
        if (data.strAwayLineupMidfield.equals("null")) {
            MID_AWAY.text = ""
        } else {
            MID_AWAY.text = data.strAwayLineupMidfield
        }
        if (data.strAwayLineupForward.equals("null")) {
            FW_AWAY.text = ""
        } else {
            FW_AWAY.text = data.strAwayLineupForward
        }
        if (data.strAwayLineupSubstitutes.equals("null")) {
            SUB_AWAY.text = ""
        } else {
            SUB_AWAY.text = data.strAwayLineupSubstitutes
        }
    }

    private fun setDetail() {
        isFavorite = presenter.isFavorite(ctx, data)
        idTeam = arrayOf(idHome.toString(), idAway.toString())
        if (presenter.isNetworkAvailable(this)) {
            for (i in 0 until idTeam.size) {
                //team detail
                presenter.geDetailTeam(this, idTeam[i], object : ServerCall {
                    override fun onSuccess(response: String) {
                        try {
                            if (presenter.isSuccess(response)) {
                                if (i == 0) {
                                    val jsonObject = JSONObject(response)
                                    Log.d("TAG", "Response $jsonObject")
                                    val message = jsonObject.getJSONArray("teams")
                                    for (i in 0 until message.length()) {
                                        val data = message.getJSONObject(i)
                                        badgeHome = data.getString("strTeamBadge")
                                    }
                                    Picasso.get()
                                        .load(badgeHome)
                                        .placeholder(R.drawable.ic_baseline_browser_not_supported_24)
                                        .error(R.drawable.ic_baseline_browser_not_supported_24)
                                        .resize(175, 175)
                                        .into(FlAG_HOME)
                                } else if (i == 1) {
                                    val jsonObject = JSONObject(response)
                                    Log.d("TAG", "Response $jsonObject")
                                    val message = jsonObject.getJSONArray("teams")
                                    for (i in 0 until message.length()) {
                                        val data = message.getJSONObject(i)
                                        badgeAway = data.getString("strTeamBadge")
                                    }
                                    Picasso.get()
                                        .load(badgeAway)
                                        .placeholder(R.drawable.ic_baseline_browser_not_supported_24)
                                        .error(R.drawable.ic_baseline_browser_not_supported_24)
                                        .resize(175, 175)
                                        .into(FlAG_AWAY)
                                }
                            }
                        } catch (e: NullPointerException) {

                        }
                    }

                    override fun onFailed(response: String) {
                    }

                    override fun onFailure(throwable: Throwable) {
                    }
                })
            }
        } else {
            Snackbar.make(detail_activity, getString(R.string.turnOn_internet)
                , Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        } else if (item.itemId == mn_favorites) {
            val intent = Intent(this, LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.lightColor = Color.BLUE
                notificationManager.createNotificationChannel(notificationChannel)
            }

            if (isFavorite) {
                presenter.removeFavorites(ctx, data)
                Log.d("TAG REMOVE", "Remove fav")
            } else {
                presenter.addFavorites(ctx, data)
                Log.d("TAG ADD", "Add fav")
            }
            isFavorite = !isFavorite
            setFavorite()
            return true
        }
        return false
    }

    override fun onBackPressed() {
        finish()
    }

}



