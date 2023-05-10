package com.loranttoth.gallerysorter

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.loranttoth.gallerysorter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    companion object {
        lateinit var myViewModel: MyViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnCal.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java).apply {
            }
            ContextCompat.startActivity(this, intent, Bundle.EMPTY)
        }

        MainActivity.myViewModel = MyViewModel().apply {
            this.contentResolver = getContentResolver()
            val i = 6
        }

        binding.myViewModel = MainActivity.myViewModel

        binding.lifecycleOwner = this



        MainActivity.myViewModel.imageList.observe(this, Observer {
            binding.rv.adapter = Adapter(
                this, it
            )
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            binding.rv.layoutManager = staggeredGridLayoutManager
            Toast.makeText(this, "${it.size} image(s) has found", Toast.LENGTH_LONG).show()
        })

        checkPerms()
    }

    fun checkPerms() {
        // If device is running SDK < 23
        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(this@MainActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                } else {
                    ActivityCompat.requestPermissions(this@MainActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                }
                return
            }
            MainActivity.myViewModel.getAllImages()
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                // ask for permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
            } else {
                MainActivity.myViewModel.getAllImages()
            }
        }
    }

}