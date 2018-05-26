package svd.joggingtimer.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by Sean on 21-5-2018.
 */
class TouchHelper(private val actionCompletionContract: ActionCompletionContract) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        println("movementflags")
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onMoved(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder, fromPos: Int, target: RecyclerView.ViewHolder, toPos: Int, x: Int, y: Int) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
        actionCompletionContract.onViewMoved(viewHolder.adapterPosition, target.adapterPosition, viewHolder.itemView.context)

    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        actionCompletionContract.onViewSwiped(viewHolder.adapterPosition, viewHolder.itemView.context)
    }

    //Gives the fade away effect when swiping
    override fun onChildDraw(c: Canvas,
                             recyclerView: RecyclerView,
                             viewHolder: RecyclerView.ViewHolder,
                             dX: Float,
                             dY: Float,
                             actionState: Int,
                             isCurrentlyActive: Boolean) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val alpha = 1 - Math.abs(dX) / recyclerView.width
            viewHolder.itemView.alpha = alpha
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


    interface ActionCompletionContract {
        fun onViewMoved(oldPosition: Int, newPosition: Int, context: Context)
        fun onViewSwiped(position: Int, context: Context)
    }
}