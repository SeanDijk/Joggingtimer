package svd.joggingtimer.util.actionhandler

abstract class ActionHandler(protected val checker: HandlerChecker) {

    fun performAction(){
        if (checker.shouldPerformAction())
            performActionImpl()
    }
    fun stopAction(){
        if (checker.shouldPerformAction())
            stopActionImpl()
    }

    protected abstract fun performActionImpl()
    protected abstract fun stopActionImpl()

}