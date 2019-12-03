package dmax.iot.arm.firmware.app

import android.app.Activity
import android.view.KeyEvent
import android.view.MotionEvent

class Main : Activity() {

    private val logic = GlueLogic()

    override fun onStart() {
        super.onStart()
        logic.start()
    }

    override fun onStop() {
        logic.stop()
        super.onStop()
    }

    override fun dispatchGenericMotionEvent(ev: MotionEvent): Boolean {
        logic.dispatchEvent(ev)
        return super.dispatchGenericMotionEvent(ev)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        logic.dispatchEvent(event)
        return super.dispatchKeyEvent(event)
    }
}
