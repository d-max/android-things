package dmax.iot.arm.firmware.app

import dmax.iot.arm.firmware.hardware.Hardware
import dmax.iot.arm.firmware.operations.Move
import dmax.iot.arm.firmware.operations.PointerDown
import dmax.iot.arm.firmware.operations.PointerUp

class GlueLogic {

    private val context = Context()
    private val hardware = Hardware()
    private val motion = Motion(hardware)

    private val move by lazy { Move(context) }
    private val up by lazy { PointerUp(context) }
    private val down by lazy { PointerDown(context) }

    fun start() {
        val arm = up()
        motion.perform(arm)
        context.arm = arm

        Thread.sleep(3_000)

        val arm2 = down()
        motion.perform(arm2)
        context.arm = arm2
    }

    fun stop() {
        hardware.shutDown()
    }
}
