package svd.joggingtimer.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import svd.joggingtimer.R
import svd.joggingtimer.activities.CreateTimerActivity
import svd.joggingtimer.domain.TimerModel
import svd.joggingtimer.util.toHHMMSS

/**
 * Created by Sean on 5-5-2018.
 */
class TimerModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
    private val jogTimeTextView = view.findViewById<TextView>(R.id.jogTimeTextView)
    private val restTimeTextView = view.findViewById<TextView>(R.id.restTimeTextView)

    private val editImageView = view.findViewById<ImageView>(R.id.editImageView)

    fun bind(model : TimerModel){
        nameTextView.text = model.name
        jogTimeTextView.text = model.joggingDuration.toHHMMSS()
        restTimeTextView.text = model.restDuration.toHHMMSS()

        editImageView.setOnClickListener {
            itemView.context.startActivity(CreateTimerActivity.createIntent(itemView.context, model))
        }
    }

}