package dmax.iot.arm.firmware.controller

import android.view.InputDevice
import android.view.InputEvent
import android.view.KeyEvent
import android.view.MotionEvent
import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.dispatcher.Dispatcher
import dmax.iot.arm.firmware.operations.Operation
import dmax.iot.arm.firmware.operations.rotateStepBase
import dmax.iot.arm.firmware.operations.rotateStepElbow
import dmax.iot.arm.firmware.operations.rotateStepWrist

private typealias Rotations = (Boolean) -> Operation

class PadController(
    private val context: Context,
    private val dispatcher: Dispatcher
) {

    private val pad = Pad()
    private var factory: (Rotations)? = null


    fun dispatchEvent(event: InputEvent) {
        if (Pad.isDpadDevice(event)) {
            pad.handleEvent(
                event = event,
                onKey = ::onKey,
                onMotion = ::onMotion
            )
        }
    }

    private fun onMotion(event: Int) {
        val clockWise = when (event) {
            Pad.LEFT -> true
            Pad.RIGHT -> false
            else -> return
        }
        val operation = factory?.invoke(clockWise)
        operation?.invoke(dispatcher)
    }

    private fun onKey(key: Int) {
        factory = when (key) {
            Pad.BUTTON_1 -> { clockWise: Boolean -> context.rotateStepBase(clockWise) }
            Pad.BUTTON_2 -> { clockWise: Boolean -> context.rotateStepElbow(clockWise) }
            Pad.BUTTON_3 -> { clockWise: Boolean -> context.rotateStepWrist(clockWise) }
            else -> null
        }
    }
}

private class Pad {

    companion object {
        const val BUTTON_1 = 1
        const val BUTTON_2 = 2
        const val BUTTON_3 = 3
        const val BUTTON_4 = 4
        const val BUTTON_L1 = 5
        const val BUTTON_L2 = 6
        const val BUTTON_R1 = 7
        const val BUTTON_R2 = 8
        const val BUTTON_MODE = 9

        const val UP = 10
        const val LEFT = 11
        const val RIGHT = 12
        const val DOWN = 13

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
        println("key $key")
        onKey(key)
    }

    private fun handleMotionEvent(event: MotionEvent, onMotion: (Int) -> Unit) {
        if (event.action != MotionEvent.ACTION_MOVE) return // todo

        val xaxis: Float = event.getAxisValue(MotionEvent.AXIS_X)
        val yaxis: Float = event.getAxisValue(MotionEvent.AXIS_RTRIGGER)

        val direction = when {
            xaxis.compareTo(-1.0f) == 0 -> LEFT
            xaxis.compareTo(1.0f) == 0 -> RIGHT
            yaxis.compareTo(-1.0f) == 0 -> UP
            yaxis.compareTo(1.0f) == 0 -> DOWN
            else -> return
        }
        println("direction $direction")
        onMotion(direction)
    }
}

