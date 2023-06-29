package com.example.findmyteam.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.findmyteam.R
import com.example.findmyteam.models.Categories
import com.example.findmyteam.models.Cities

class LocationsAdapter(context: Context, list:List<Cities>):ArrayAdapter<Cities>(context,0,list) {
    private var layoutInflater= LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View =layoutInflater.inflate(R.layout.cities_item,null,false )
        return view(view,position)
    }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cv=convertView
        if(cv==null)
            cv=layoutInflater.inflate(R.layout.cities_item,parent,false)
        return view(cv!!,position)
    }
    private fun view(view: View, position: Int): View {
        val cities: Cities =getItem(position)?:return view
        val cityName=view.findViewById<TextView>(R.id.cityName)
        cityName?.text=cities.name
        return view

    }
}