package dmax.iot.arm.firmware.app

import android.view.InputEvent
import dmax.iot.arm.firmware.controller.Pad
import dmax.iot.arm.firmware.controller.Pad.Companion.BUTTON_1
import dmax.iot.arm.firmware.controller.Pad.Companion.BUTTON_2
import dmax.iot.arm.firmware.controller.Pad.Companion.BUTTON_3
import dmax.iot.arm.firmware.controller.Pad.Companion.LEFT
import dmax.iot.arm.firmware.controller.Pad.Companion.RIGHT
import dmax.iot.arm.firmware.dispatcher.Dispatcher
import dmax.iot.arm.firmware.hardware.Hardware
import dmax.iot.arm.firmware.motion.Motion
import dmax.iot.arm.firmware.operations.Operation
import dmax.iot.arm.firmware.operations.rotateStepBase
import dmax.iot.arm.firmware.operations.rotateStepElbow
import dmax.iot.arm.firmware.operations.rotateWrist

typealias Rotations = (Boolean) -> Operation

class GlueLogic {

    private val pad = Pad()
    private val context = Context()
    private val hardware = Hardware()
    private val motion = Motion(hardware)
    private val dispatcher = Dispatcher(context, motion)

    private var factory: (Rotations)? = null

    fun start() {
        dispatcher.dispatch()
    }

    fun stop() {
        dispatcher.terminate()
        hardware.shutDown()
    }

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
            LEFT -> true
            RIGHT -> false
            else -> return
        }
        val operation = factory?.invoke(clockWise)
        operation?.invoke(dispatcher)
    }

    private fun onKey(key: Int) {
        factory = when (key) {
            BUTTON_1 -> { clockWise: Boolean -> context.rotateStepBase(clockWise) }
            BUTTON_2 -> { clockWise: Boolean -> context.rotateStepElbow(clockWise) }
            BUTTON_3 -> { clockWise: Boolean -> context.rotateWrist(clockWise) }
            else -> null
        }
    }
}
