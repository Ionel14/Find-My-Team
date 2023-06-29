package com.example.findmyteam.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.findmyteam.R
import com.example.findmyteam.fragments.AddAnnouncementFragment
import com.example.findmyteam.models.Categories

class CategoriesAdapter(context:Context, list:List<Categories>):ArrayAdapter<Categories>(context,0,list) {
    private var layoutInflater=LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
       val view: View=layoutInflater.inflate(R.layout.categories_item,null,false )
        return view(view,position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cv=convertView
        if(cv==null)
            cv=layoutInflater.inflate(R.layout.categories_item,parent,false)
        return view(cv!!,position)
    }
    private fun view(view: View, position: Int): View {
val categories:Categories=getItem(position)?:return view
    val categoryName=view.findViewById<TextView>(R.id.categoryName)
        val categoryIcon=view.findViewById<ImageView>(R.id.categoryIcon)
        categoryName?.text=categories.name
        when (categories) {
            Categories.Football -> {
                val drawable = ContextCompat.getDrawable(view.context, R.drawable.football_ball)
                categoryIcon?.setImageDrawable(drawable)
            }
            Categories.Basketball -> {
                val drawable = ContextCompat.getDrawable(view.context, R.drawable.basketball)
                categoryIcon?.setImageDrawable(drawable)
            }
            Categories.Tennis -> {
                val drawable = ContextCompat.getDrawable(view.context, R.drawable.tennis)
                categoryIcon?.setImageDrawable(drawable)
            }
            else -> {
                val drawable = ContextCompat.getDrawable(view.context, R.drawable.other)
                categoryIcon?.setImageDrawable(drawable)
            }
        }
        return view
    }
}