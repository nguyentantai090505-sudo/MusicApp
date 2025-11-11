package com.example.tktmusicapp.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.tktmusicapp.domain.model.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext


@HiltViewModel
class PlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context

) : ViewModel() {

    private var exoPlayer: ExoPlayer? = null

    val currentSong = mutableStateOf<Song?>(null)
    val isPlaying = mutableStateOf(false)
    val currentPosition = mutableStateOf(0L)
    val totalDuration = mutableStateOf(0L)

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            this@PlayerViewModel.isPlaying.value = isPlaying
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            updateSongInfo()
        }
    }

    init {
        exoPlayer = ExoPlayer.Builder(context).build()
        exoPlayer?.addListener(playerListener)
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                if (exoPlayer?.isPlaying == true) {
                    currentPosition.value = exoPlayer?.currentPosition ?: 0L
                    totalDuration.value = exoPlayer?.duration ?: 0L
                }
            }
        }
    }

    fun play(song: Song) {
        currentSong.value = song
        exoPlayer?.let {
            val mediaItem = MediaItem.fromUri(song.songUrl)
            it.setMediaItem(mediaItem)
            it.prepare()
            it.play()
        }
    }

    fun pause() {
        exoPlayer?.pause()
    }

    fun resume() {
        exoPlayer?.play()
    }

    fun seek(position: Long) {
        exoPlayer?.seekTo(position)
    }

    private fun updateSongInfo() {
        // Cập nhật thông tin bài hát nếu cần
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
    }
}
