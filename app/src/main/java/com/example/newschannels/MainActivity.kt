package com.example.newschannels

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Keep the screen on while the app is in use
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        playerView = findViewById(R.id.player_view)
        showStreamSelectionDialog()
    }


private fun showStreamSelectionDialog() {
    val streamUrls = arrayOf(
        "https://nw18live.cdn.jio.com/bpk-tv/News18_Bihar_Jharkhand_NW18_MOB/output01/News18_Bihar_Jharkhand_NW18_MOB-audio_98835_hin=98800-video=3724000.m3u8",
        "https://aajtaklive-amd.akamaized.net/hls/live/2014416-b/aajtak/aajtaklive/live_720p/chunks.m3u8",
        "https://d3vmwjy7v49lo5.cloudfront.net/index_4.m3u8?akes=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MjI4Nzg2NTl9.SmBFSMbvbvTIEZHtqmaQ0VhedAaW-HGNQIOzIpDTFtw",
        "https://ndtvindiaelemarchana.akamaized.net/hls/live/2003679-b/ndtvindia/masterb_720p@3.m3u8"
    )
    val streamTitles = arrayOf("News18", "AAJ_TAK", "Zee news", "NDTV INDIA")

    val builder = AlertDialog.Builder(this)
    builder.setTitle("Choose a News Channel")
    builder.setItems(streamTitles) { _, which ->
        initializePlayer(streamUrls[which])
    }
    builder.setCancelable(false) // Prevent dialog dismissal with back button
    builder.show()
}

    private fun initializePlayer(url: String) {
        // Initialize the ExoPlayer
        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        // Set the media item to be played
        val uri = Uri.parse(url)
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)

        // Prepare the player
        player.prepare()
        player.playWhenReady = true
    }

    override fun onBackPressed() {
        // Directly exit the application when the back button is pressed
        finishAffinity() // This closes the app
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }
}
