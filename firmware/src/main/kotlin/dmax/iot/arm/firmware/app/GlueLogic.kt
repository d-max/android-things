package dmax.iot.arm.firmware.app

import android.view.InputEvent
import dmax.iot.arm.firmware.controller.Pad
import dmax.iot.arm.firmware.controller.Pad.Companion.BUTTON_1
import dmax.iot.arm.firmware.controller.Pad.Companion.BUTTON_2
import dmax.iot.arm.firmware.controller.Pad.Companion.BUTTON_3
import dmax.iot.arm.firmware.controller.Pad.Companion.LEFT
import dmax.iot.arm.firmware.controller.Pad.Companion.RIGHT
import dmax.iot.arm.firmware.hardware.Hardware
import dmax.iot.arm.firmware.motion.Motion
import dmax.iot.arm.firmware.operations.Rotate
import dmax.iot.arm.firmware.operations.RotateBase
import dmax.iot.arm.firmware.operations.RotateElbow
import dmax.iot.arm.firmware.operations.RotateWrist

class GlueLogic {

    private val context = Context()
    private val hardware = Hardware()
    private val motion = Motion(hardware)
    private val pad = Pad()

    fun start() {
    }

    fun stop() {
        hardware.shutDown()
    }

    private var rotation: Rotate? = null

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
        when (event) {
            LEFT -> rotation?.invoke(clockWise = true)
            RIGHT -> rotation?.invoke(clockWise = false)
        }
    }

    private fun onKey(key: Int) {
        rotation = when (key) {
            BUTTON_1 -> {
                RotateBase(context, motion)
            }
            BUTTON_2 -> {
                RotateElbow(context, motion)
            }
            BUTTON_3 -> {
                RotateWrist(context, motion).apply {
                    invoke(true)
                    Thread.sleep(100)
                    invoke(false)
                }
                null
            }
            else -> null
        }
    }


}
