package dmax.iot.arm.firmware.app

import android.os.Handler
import android.view.InputEvent
import dmax.iot.arm.firmware.controller.Pad
import dmax.iot.arm.firmware.hardware.Hardware
import dmax.iot.arm.firmware.mechanics.Arm
import dmax.iot.arm.firmware.operations.Move
import dmax.iot.arm.firmware.operations.Move1
import dmax.iot.arm.firmware.operations.Move3
import dmax.iot.arm.firmware.operations.PointerDown
import dmax.iot.arm.firmware.operations.PointerUp

class GlueLogic {

    private val context = Context()
    private val hardware = Hardware()
    private val motion = Motion(hardware)

    private val move by lazy { Move(context) }
    private val up by lazy { PointerUp(context) }
    private val down by lazy { PointerDown(context) }

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
        }, 10000)
    }

    fun start() {
        Handler().schedule()
    }

    fun stop() {
        hardware.shutDown()
    }

    private fun Arm.update() {
        motion.perform(this)
        context.arm = this
    }
}
