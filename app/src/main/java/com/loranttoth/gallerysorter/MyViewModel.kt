package com.loranttoth.gallerysorter

import android.content.ContentResolver
import android.content.ContentUris
import java.text.SimpleDateFormat
import android.net.Uri
import android.provider.MediaStore
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MyViewModel: ViewModel(), Observable {

    val imagesAll = mutableListOf<DataClass>()
    var imageList = MutableLiveData<MutableList<DataClass>>()
    var dateText = MutableLiveData<String>()
    var actTime = 0L
    lateinit var contentResolver:ContentResolver

    fun setDate(year: Int, month: Int, date: Int) {
        val cal1 = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, date)
            set(Calendar.HOUR, 0)
            set(Calendar.MINUTE, 0)
        }

        val cal2 = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, date)
            add(Calendar.DAY_OF_MONTH, 1)   //beginning ot the next day
            set(Calendar.HOUR, 0)
            set(Calendar.MINUTE,0)
        }

        val num1 = cal1.timeInMillis
        val num2 = cal2.timeInMillis

        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        dateText.value = simpleDateFormat.format(cal1.time).toString()
        actTime = cal1.timeInMillis

        makeImageList(num1, num2)
    }

    fun makeImageList(num1: Long, num2: Long) {
        val list = imagesAll.filter{ item -> item.time in num1 until num2 }.toMutableList()
        imageList.value = list
    }

    fun getAllImages() {

        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns.ORIENTATION,
        MediaStore.Images.ImageColumns.WIDTH, MediaStore.Images.ImageColumns.HEIGHT)

        val query = contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )
        var imageUri: Uri

        var num = 0

        query.use { cursor ->
            cursor?.let {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
                val orientColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION)
                val widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
                val heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)
                while (cursor.moveToNext()) {
                    imageUri = ContentUris.withAppendedId(uri, cursor.getLong(idColumn))

                    var creTime = cursor.getLong(dateColumn)

                    var orientation = cursor.getInt(orientColumn)
                    var width = cursor.getInt(widthColumn)
                    var height = cursor.getInt(widthColumn)
                    imagesAll.add(DataClass("Title$num", "subTitle$num", imageUri.toString(), orientation, width, height, creTime))
                    num++
                }
                val cal = Calendar.getInstance()
                setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
            }
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}