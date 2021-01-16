package com.example.uas0120

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uas0120.R
import com.example.uas0120.Adapter
import com.example.uas0120.Eventsitem
import com.example.uas0120.getLongDate

import kotlinx.android.synthetic.main.item.view.*

class AdapterMatch(private val onClickListener: (position: Int) -> Unit) : Adapter<RecyclerView.ViewHolder, Eventsitem>() {

    companion object {
        const val TRANSACTION_ITEM_TYPE = 1
    }

    init {
        setHasStableIds(true)
    }

    private var data: MutableList<Eventsitem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item, parent, false)
        return ProductViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> holder.bindData(data[position],position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TRANSACTION_ITEM_TYPE
    }

    override fun addAllData(data: MutableList<Eventsitem>) {
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun addData(data: Eventsitem) {
        this.data.add(data)
        this.notifyDataSetChanged()
    }

    override fun getDataAt(position: Int): Eventsitem {
        return data[position]
    }

    override fun getAllData(): MutableList<Eventsitem> {
        return data
    }

    override fun setData(data: MutableList<Eventsitem>) {

        this.data = data
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}

class ProductViewHolder(viewItem: View, onClickListener: (position: Int) -> Unit) : RecyclerView.ViewHolder(viewItem) {
    init {
        viewItem.setOnClickListener {
            onClickListener(adapterPosition)
        }
    }
    fun bindData(data: Eventsitem, position : Int){
        if((data.intHomeScore!!.equals("null")||data.intAwayScore!!.equals("null"))&&!data.strHomeTeam!!.equals("null")) {
            itemView.date.text = getLongDate(data.dateEvent)
            itemView.ID_HOME_TEAM.text = data.strHomeTeam
            itemView.ID_HOME_SCORE.visibility = View.GONE
            itemView.ID_AWAY_TEAM.text = data.strAwayTeam
            itemView.ID_AWAY_SCORE.visibility = View.GONE
        }else if(!data.strHomeTeam!!.equals("null")) {
            itemView.date.text = getLongDate(data.dateEvent)
            itemView.ID_HOME_TEAM.text = data.strHomeTeam
            itemView.ID_HOME_SCORE.text = data.intHomeScore
            itemView.ID_AWAY_TEAM.text = data.strAwayTeam
            itemView.ID_AWAY_SCORE.text = data.intAwayScore
            itemView.ID_HOME_SCORE.visibility = View.VISIBLE
            itemView.ID_AWAY_SCORE.visibility = View.VISIBLE
        }else{
            itemView.date.text = getLongDate(data.dateEvent)
            itemView.ID_HOME_TEAM.text = ""
            itemView.ID_HOME_SCORE.text = ""
            itemView.ID_AWAY_TEAM.text = ""
            itemView.ID_AWAY_SCORE.text = ""
        }
    }
}