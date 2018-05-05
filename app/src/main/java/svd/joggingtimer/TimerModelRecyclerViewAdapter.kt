package svd.joggingtimer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import svd.joggingtimer.activities.TimerActivity
import svd.joggingtimer.model.TimerModel
import svd.joggingtimer.viewholders.TimerModelViewHolder

/**
 * Created by Sean on 5-5-2018.
 */
class TimerModelRecyclerViewAdapter(val items: ArrayList<TimerModel>): RecyclerView.Adapter<TimerModelViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerModelViewHolder =
            TimerModelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_timer, parent, false))

    override fun getItemCount(): Int = items.count()
    override fun getItemViewType(position: Int): Int = R.layout.list_item_timer

    override fun onBindViewHolder(holder: TimerModelViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(TimerActivity.getIntent(holder.itemView.context, item))
        }
    }

}