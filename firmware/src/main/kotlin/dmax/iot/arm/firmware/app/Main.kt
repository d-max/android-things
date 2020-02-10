package dmax.iot.arm.firmware.app

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import dmax.iot.arm.firmware.R

class Main : Activity() {

    private val allocator by lazy { Allocator() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.controller)
        allocator.uiController.init(window.decorView.rootView)
    }

    override fun onStart() {
        super.onStart()
        allocator.hardware.reset()
        allocator.dispatcher.dispatch()
    }

    override fun onStop() {
        allocator.dispatcher.terminate()
        allocator.hardware.shutDown()
        super.onStop()
    }

    override fun dispatchGenericMotionEvent(ev: MotionEvent): Boolean {
        allocator.padController.dispatchEvent(ev)
        return super.dispatchGenericMotionEvent(ev)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        allocator.padController.dispatchEvent(event)
        return super.dispatchKeyEvent(event)
    }
}
