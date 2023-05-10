package com.loranttoth.gallerysorter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        var imageLink = intent.getStringExtra(EXTRA_MESSAGE_LINK)

        var orientation = intent.getFloatExtra(EXTRA_MESSAGE_ORIENTATION, 0f)

        var imageView = findViewById<View>(R.id.imageView) as ImageView

        Picasso.get().load(imageLink).fit().rotate(orientation).centerInside().placeholder(R.drawable.image_regular).into(imageView)

        var button = findViewById<View>(R.id.btnClose) as Button
        button.setOnClickListener {
            super.finish()
        }
    }
}