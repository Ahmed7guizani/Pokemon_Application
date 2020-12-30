package com.example.pokemon_application.presentation.list

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemon_application.domain.entity.Pokemon
import com.example.pokemon_application.R


class ListAdapter(
    private val values: MutableList<Pokemon?>?,
    private val context: Context,
    private var listener: OnItemClickListener? = null


) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Pokemon?)
    }

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(
        layout
    ) {
        // each data item is just a string in this case
        var txtHeader: TextView
        var txtFooter: TextView
        var imageView: ImageView

        init {
            txtHeader = layout.findViewById<View>(R.id.firstLine) as TextView
            txtFooter = layout.findViewById<View>(R.id.secondLine) as TextView
            imageView = layout.findViewById<View>(R.id.icon) as ImageView

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val inflater = LayoutInflater.from(
            parent.context
        )
        val v: View = inflater.inflate(R.layout.row_layout, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val currentPokemon = values?.get(position)
        Glide.with(context).load(currentPokemon?.img).into(holder.imageView)
        holder.txtHeader.text = currentPokemon?.name
        holder.txtFooter.text = currentPokemon?.num
        holder.itemView.setOnClickListener { listener?.onItemClick(currentPokemon) }
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return values?.size ?: 0

    }

}


