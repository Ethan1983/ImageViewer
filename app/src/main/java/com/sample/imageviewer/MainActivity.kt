package com.sample.imageviewer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var imageView : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.image)
        handleLaunchIntent( intent )
    }

    private fun updateImage( uri : Uri ) {
        Toast.makeText( this, "Process Image and upload to cloud $uri",
            Toast.LENGTH_SHORT ).show()
        //Reset intent or intent parameters does not update the version tracked by Android Framework
        intent = null
    }

    private fun handleLaunchIntent( intent : Intent) {
        when( intent.action ) {
            Intent.ACTION_SEND -> {

                intent.type?.let {
                    if( it.startsWith( "image/" ) ) {

                        if( intent.flags and Intent.FLAG_GRANT_READ_URI_PERMISSION !=
                            Intent.FLAG_GRANT_READ_URI_PERMISSION ) {
                            Toast.makeText( this, "Unable to read - Permission denied",
                                Toast.LENGTH_SHORT ).show()
                        } else {
                            val uri : Uri = intent.getParcelableExtra( Intent.EXTRA_STREAM )
                            updateImage( uri )
                        }
                    }
                }
            }
        }
    }

}
