package svd.joggingtimer.recyclerview

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import svd.joggingtimer.R
import svd.joggingtimer.activities.TimerActivity
import svd.joggingtimer.domain.TimerModel
import svd.joggingtimer.util.OnSingleClickListener
import android.R.attr.data
import svd.joggingtimer.database.TimerDatabase


/**
 * Created by Sean on 5-5-2018.
 */
class TimerModelRecyclerViewAdapter(private var items: MutableList<TimerModel> =ArrayList()): RecyclerView.Adapter<TimerModelViewHolder>(), TouchHelper.ActionCompletionContract {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerModelViewHolder =
            TimerModelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_timer, parent, false))

    override fun getItemCount(): Int = items.count()
    override fun getItemViewType(position: Int): Int = R.layout.list_item_timer

    override fun onBindViewHolder(holder: TimerModelViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(TimerActivity.createIntent(holder.itemView.context, item))
        }
    }

    override fun onViewMoved(oldPosition: Int, newPosition: Int, context: Context) {
        items.add(newPosition, items.removeAt(oldPosition))
        notifyItemMoved(oldPosition, newPosition)


        //        Needs O(n) for updating the position... TODO find a better way
        //val db = TimerDatabase.getInstance(context)
        //items.forEach { item -> db.execute { it.timerModelDao().insert(item) } }
    }

    override fun onViewSwiped(position: Int, context: Context) {
        //Remove the item from the adapter list, remove it from the database and then notify
        val item = items.removeAt(position)
        notifyItemRemoved(position)
        TimerDatabase.getInstance(context).execute { it.timerModelDao().delete(item) }

        //Undo snackbar
        if (context is Activity){
            //Unhappy about this line of code, should the adapter really be responsible for making an undo snackbar?
            Snackbar.make(context.window.decorView.findViewById<View>(android.R.id.content), R.string.button_stop, Snackbar.LENGTH_SHORT)
                    .setAction("Undo", object: OnSingleClickListener() {
                        override fun onSingleClick(v: View?) {
                            items.add(position, item)
                            notifyItemInserted(position)
                            TimerDatabase.getInstance(context).execute { it.timerModelDao().insert(item) }
                        }
                    })
                    .show()
        }
    }


    fun setData(newData: MutableList<TimerModel>) {
        this.items = newData
        notifyDataSetChanged()
    }

}