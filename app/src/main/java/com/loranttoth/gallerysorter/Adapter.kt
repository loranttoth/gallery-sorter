package com.loranttoth.gallerysorter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

const val EXTRA_MESSAGE_LINK = "com.loranttoth.gallerysorter.IMAGELINK"
const val EXTRA_MESSAGE_ORIENTATION = "com.loranttoth.gallerysorter.ORIENTATION"

class Adapter(private val context: Context, private val list: List<DataClass>) : RecyclerView.Adapter<Adapter.MyVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
        return MyVH(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        val data=list[position]

        Picasso.get().load(data.imageLink).rotate(data.orientation.toFloat()).
        resize((data.width*0.2).toInt(),(data.height*0.2).toInt()).centerInside().placeholder(R.drawable.image_regular).
        into(holder.itemView.findViewById<ImageView>(R.id.image))
        holder.itemView.setOnClickListener {
            val imageLink = data.imageLink
            val intent = Intent(context, ImageActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE_LINK, imageLink)
                putExtra(EXTRA_MESSAGE_ORIENTATION, data.orientation.toFloat())
            }
            startActivity(context, intent, Bundle.EMPTY)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyVH (itemView: View):
        RecyclerView.ViewHolder(itemView)

}
