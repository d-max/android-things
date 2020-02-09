package dmax.iot.arm.firmware.app

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import dmax.iot.arm.firmware.R

class Main : Activity() {

    private val logic by lazy { GlueLogic() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.controller)

    }

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
