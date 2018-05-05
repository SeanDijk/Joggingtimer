package svd.joggingtimer.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import svd.joggingtimer.R
import svd.joggingtimer.model.TimerModel
import svd.joggingtimer.toHHMMSS
import java.sql.Time

/**
 * Created by Sean on 5-5-2018.
 */
class TimerModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
    private val jogTimeTextView = view.findViewById<TextView>(R.id.jogTimeTextView)
    private val restTimeTextView = view.findViewById<TextView>(R.id.restTimeTextView)

    private val editImageView = view.findViewById<ImageView>(R.id.editImageView)
    private val deliteImageView = view.findViewById<ImageView>(R.id.deleteImageView)

    fun bind(model : TimerModel){
        nameTextView.text = model.name
        jogTimeTextView.text = model.joggingDuration.toHHMMSS() //todo convert to HH:MM:SS
        restTimeTextView.text = model.restDuration.toHHMMSS() //todo convert to HH:MM:SS
    }

}