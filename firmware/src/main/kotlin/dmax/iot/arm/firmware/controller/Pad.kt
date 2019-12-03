package dmax.iot.arm.firmware.controller

import android.util.Log
import android.view.InputDevice
import android.view.InputEvent
import android.view.KeyEvent
import android.view.MotionEvent

class Pad {

    companion object {
        internal const val BUTTON_1 = 1
        internal const val BUTTON_2 = 2
        internal const val BUTTON_3 = 3
        internal const val BUTTON_4 = 4
        internal const val BUTTON_L1 = 5
        internal const val BUTTON_L2 = 6
        internal const val BUTTON_R1 = 7
        internal const val BUTTON_R2 = 8
        internal const val BUTTON_MODE = 9

        internal const val UP = 10
        internal const val LEFT = 11
        internal const val RIGHT = 12
        internal const val DOWN = 13

        fun isDpadDevice(event: InputEvent): Boolean =
            // Check that input comes from a device with directional pads.
            event.source and InputDevice.SOURCE_DPAD != InputDevice.SOURCE_DPAD
    }

    fun handleEvent(event: InputEvent, onKey: (Int) -> Unit, onMotion: (Int) -> Unit) {
        when(event) {
            is KeyEvent -> handleKeyEvent(event, onKey)
            is MotionEvent -> handleMotionEvent(event, onMotion)
        }
    }

    private fun handleKeyEvent(event: KeyEvent, onKey: (Int) -> Unit) {
        Log.d("###", "keyEvent action: " + event.action)
        Log.d("###", "keyEvent code: " + event.keyCode)
        if (event.action != KeyEvent.ACTION_DOWN) return // todo

        val key = when(event.keyCode) {
            KeyEvent.KEYCODE_BUTTON_1 -> BUTTON_1
            KeyEvent.KEYCODE_BUTTON_2 -> BUTTON_2
            KeyEvent.KEYCODE_BUTTON_3 -> BUTTON_3
            KeyEvent.KEYCODE_BUTTON_4 -> BUTTON_4
            KeyEvent.KEYCODE_BUTTON_5 -> BUTTON_L1
            KeyEvent.KEYCODE_BUTTON_6 -> BUTTON_R1
            KeyEvent.KEYCODE_BUTTON_7 -> BUTTON_L2
            KeyEvent.KEYCODE_BUTTON_8 -> BUTTON_R2
            KeyEvent.KEYCODE_BUTTON_9 -> BUTTON_MODE
            else -> return
        }
        onKey(key)
    }

    private fun handleMotionEvent(event: MotionEvent, onMotion: (Int) -> Unit) {
        Log.d("###", "motionEvent action: " + event.action)
        if (event.action != MotionEvent.ACTION_MOVE) return // todo

        val xaxis: Float = event.getAxisValue(MotionEvent.AXIS_X)
//        val yaxis: Float = event.getAxisValue(MotionEvent.AXIS_RTRIGGER)

        Log.d("###", "motionEvent action: $xaxis")

        val direction = when {
            xaxis.compareTo(-1.0f) == 0 -> LEFT
            xaxis.compareTo(1.0f) == 0 -> RIGHT
//            yaxis.compareTo(-1.0f) == 0 -> UP
//            yaxis.compareTo(1.0f) == 0 -> DOWN
            else -> return
        }
        onMotion(direction)
    }
}
