package com.example.tktmusicapp.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.tktmusicapp.domain.model.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var exoPlayer: ExoPlayer? = null
    private var playlist = mutableListOf<Song>()
    private var currentTrackIndex = -1

    val currentSong = mutableStateOf<Song?>(null)
    val isPlaying = mutableStateOf(false)
    val currentPosition = mutableStateOf(0L)
    val totalDuration = mutableStateOf(0L)
    private val isSeeking = mutableStateOf(false)

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            this@PlayerViewModel.isPlaying.value = isPlaying
        }

        override fun onPlaybackStateChanged(state: Int) {
            if (state == Player.STATE_ENDED) {
                playNext()
            }
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
                delay(200L) // Cập nhật nhanh hơn để seekbar mượt hơn
                if (exoPlayer?.isPlaying == true && !isSeeking.value) {
                    currentPosition.value = exoPlayer?.currentPosition ?: 0L
                    totalDuration.value = exoPlayer?.duration ?: 0L
                }
            }
        }
    }

    fun setPlaylist(songs: List<Song>, startPlayingFromIndex: Int = 0){
        playlist.clear()
        playlist.addAll(songs)
        currentTrackIndex = if (startPlayingFromIndex in songs.indices) startPlayingFromIndex else 0
        if (playlist.isNotEmpty()) {
            play(playlist[currentTrackIndex])
        }
    }

    fun play(song: Song) {
        val indexInPlaylist = playlist.indexOf(song)
        if (indexInPlaylist != -1) {
            currentTrackIndex = indexInPlaylist
        } else {
            playlist.clear()
            playlist.add(song)
            currentTrackIndex = 0
        }
        currentSong.value = song

        if (song.songUrl.isNotBlank()) {
            exoPlayer?.let {
                val mediaItem = MediaItem.fromUri(song.songUrl)
                it.setMediaItem(mediaItem)
                it.prepare()
                it.play()
            }
        }
    }

    fun playNext() {
        if (playlist.isNotEmpty()) {
            currentTrackIndex = (currentTrackIndex + 1) % playlist.size
            play(playlist[currentTrackIndex])
        }
    }

    fun playPrevious() {
        if (playlist.isNotEmpty()) {
            currentTrackIndex = if (currentTrackIndex - 1 < 0) playlist.size - 1 else currentTrackIndex - 1
            play(playlist[currentTrackIndex])
        }
    }

    fun pause() {
        exoPlayer?.pause()
    }

    fun resume() {
        exoPlayer?.play()
    }

    fun onSeekStart() {
        isSeeking.value = true
    }

    fun onSeek(position: Float) {
        currentPosition.value = position.toLong()
    }

    fun onSeekFinished(position: Long) {
        exoPlayer?.seekTo(position)
        isSeeking.value = false
    }

    private fun updateSongInfo() {
        // Cập nhật thông tin bài hát nếu cần
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
    }
}
