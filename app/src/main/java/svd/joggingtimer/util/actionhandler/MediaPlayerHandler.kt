package svd.joggingtimer.util.actionhandler

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.preference.PreferenceManager
import android.support.annotation.RawRes
import svd.joggingtimer.R

/**
 * Class to handle the usage of the MediaPlayer.
 * Uses the setting in SharedPrefferences to check if the sound should actually be played.
 * This could be more loosely coupled using dependency injection
 */
class MediaPlayerHandler(context: Context, checker: HandlerChecker, @RawRes private val audioFile: Int): ActionHandler(checker) {
    private var mediaPlayer = MediaPlayer.create(context, audioFile)

    override fun performActionImpl() {
        mediaPlayer.start()
    }

    override fun stopActionImpl() {
        if (mediaPlayer.isPlaying) {
            println("stopped")
            mediaPlayer.pause()
            mediaPlayer.stop()
            mediaPlayer.prepare()
        }
    }

}