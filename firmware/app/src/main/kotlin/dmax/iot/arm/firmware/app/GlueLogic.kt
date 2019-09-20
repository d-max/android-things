package dmax.iot.arm.firmware.app

import dmax.iot.arm.firmware.hardware.Hardware
import dmax.iot.arm.firmware.mechanics.Arm
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
        move(5, 5).update()
        Thread.sleep(1_000)
        up().update()
        Thread.sleep(1_000)
        down().update()
    }

    fun stop() {
        hardware.shutDown()
    }

    private fun Arm.update() {
        motion.perform(this)
        context.arm = this
    }
}
