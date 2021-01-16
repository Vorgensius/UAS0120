package com.example.uas0120

import androidx.recyclerview.widget.RecyclerView

abstract class Adapter<H, T> : RecyclerView.Adapter<H>() where H : RecyclerView.ViewHolder {
    abstract fun addAllData(data: MutableList<T>)
    abstract fun addData(data: T)
    abstract fun getDataAt(position: Int): T
    abstract fun getAllData(): MutableList<T>
    abstract fun setData(data: MutableList<T>)
}