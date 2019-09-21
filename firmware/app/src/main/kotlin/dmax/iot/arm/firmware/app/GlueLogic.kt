package dmax.iot.arm.firmware.app

import android.os.Handler
import android.util.Log
import android.view.InputEvent
import dmax.iot.arm.firmware.controller.Pad
import dmax.iot.arm.firmware.controller.Pad.Companion.BUTTON_1
import dmax.iot.arm.firmware.controller.Pad.Companion.BUTTON_2
import dmax.iot.arm.firmware.controller.Pad.Companion.LEFT
import dmax.iot.arm.firmware.controller.Pad.Companion.RIGHT
import dmax.iot.arm.firmware.hardware.Hardware
import dmax.iot.arm.firmware.mechanics.Arm
import dmax.iot.arm.firmware.operations.Action
import dmax.iot.arm.firmware.operations.Move
import dmax.iot.arm.firmware.operations.Move1
import dmax.iot.arm.firmware.operations.Move3
import dmax.iot.arm.firmware.operations.PointerDown
import dmax.iot.arm.firmware.operations.PointerUp
import dmax.iot.arm.firmware.operations.Rotate
import dmax.iot.arm.firmware.operations.Rotate1

class GlueLogic {

    private val context = Context()
    private val hardware = Hardware()
    private val motion = Motion(hardware)
    private val pad = Pad()

    private val move by lazy { Move(context) }
    private val up by lazy { PointerUp(context) }
    private val down by lazy { PointerDown(context) }
    private val rotate by lazy { Rotate(context) }
    private val rotate1 by lazy { Rotate1(context) }

    private var off = false

    private fun Handler.schedule() {
        postDelayed({
            if (off) {
                Move1(context).invoke().update()
            } else {
                Move3(context).invoke().update()
            }
            off = !off
            schedule()
        }, 3000)
    }

    fun start() {
//        Handler().schedule()
//        Move1(context).invoke().update()
    }

    fun stop() {
        hardware.shutDown()
    }

    private var current: Action? = null

    fun dispatchEvent(event: InputEvent) {
        if (Pad.isDpadDevice(event)) {
            pad.handleEvent(
                event = event,
                onKey = {
                    current = when (it) {
                        BUTTON_1 -> rotate
                        BUTTON_2 -> rotate1
                        else -> null
                    }
                },
                onMotion = {
                    when (it) {
                        LEFT -> {
                            /*rotate.invoke(true).update() */
                            Log.d("@@@", "left")
                            current?.invoke(true)?.update()
                        }
                        RIGHT -> {
                            /*rotate.invoke(false).update() */
                            Log.d("@@@", "right")
                            current?.invoke(false)?.update()
                        }
                    }
                }
            )
        }
    }

    private fun Arm.update() {
        motion.perform(this)
        context.arm = this
    }
}
