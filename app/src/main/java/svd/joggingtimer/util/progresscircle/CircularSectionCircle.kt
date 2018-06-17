package svd.joggingtimer.util.progresscircle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CircularSectionCircle(context: Context, attrs: AttributeSet) : View(context, attrs) {
    //Class containing a color and the weight it has. Creates a paint object for the color.
    class CircleParam(val color: Int, val weight: Int){
        val myPaint = createPaint(color)

        private fun createPaint(color: Int): Paint {
            return Paint().apply {
                this.color = color
                strokeWidth = 20f
                style = Paint.Style.STROKE
            }
        }
    }
    //The width of the line.
    private val strokeWidth =20f

    //The list of all the Circle params.
    private var paramsList: List<CircleParam> = ArrayList()

    //The total weight calculated when a new param list is set.
    private var totalWeight = 0

    //The necessary angles for drawing that are calculated when a new param list is set.
    var anglePairs = ArrayList<Pair<Float, Float>>() // startAngle, sweepAngle

    //The starting angle. Makes sure the circle starts at 12 O'clock
    private val startPosition = -90f

    //Filter to create the background circle.
    private val lighterColorFilter = PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.LIGHTEN)


    private val rect = RectF()

    //The offset in degrees.
    var offset = 0f
    set(value) {
        field = value
        invalidate()
    }

    init {
        //Parameters used for testing purposes
//        setParams(listOf(
//                CircleParam(Color.BLUE, 25),
//                CircleParam(Color.GREEN, 25),
//                CircleParam(Color.YELLOW, 25),
//                CircleParam(Color.CYAN, 25)
//        ))
    }

    override fun onDraw(canvas: Canvas) {

        var offsetLeft = offset
        anglePairs.forEachIndexed { index, pair ->

            //Background
            canvas.drawArc(rect, pair.first, pair.second, false, paramsList[index].myPaint.apply { colorFilter = lighterColorFilter })

            //Foreground
            if(offsetLeft> 0){
                var calculatedValue = pair.second - offsetLeft
                if(calculatedValue <0)
                    calculatedValue = 0f

                canvas.drawArc(rect, pair.first+offsetLeft, calculatedValue, false, paramsList[index].myPaint.apply { colorFilter = null })
                offsetLeft -= pair.second
            } else{
                canvas.drawArc(rect, pair.first, pair.second, false, paramsList[index].myPaint.apply { colorFilter = null })

            }

        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val height = View.getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = Math.min(width, height)
        setMeasuredDimension(min, min)
        rect.set(0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2)
    }

    fun setParams(params: List<CircleParam>){
        this.paramsList = params
        totalWeight = params.sumBy { it.weight}

        anglePairs.clear()
        var currentStartAngle = startPosition
        paramsList.forEach{
            val sweepAngle = it.weight*(360f)/totalWeight
            anglePairs.add(Pair(currentStartAngle, sweepAngle))
            currentStartAngle += sweepAngle
        }

    }

    fun setOffsetWeight(offsetWeight: Float) {
        offset =  (360f/totalWeight.toFloat() * offsetWeight)
    }


}