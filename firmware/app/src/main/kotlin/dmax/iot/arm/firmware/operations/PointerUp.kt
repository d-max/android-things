package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.mechanics.Arm
import dmax.iot.arm.firmware.mechanics.Joint

class PointerUp(private val context: Context) {

    companion object {
        private const val UP_ANGLE = 90
    }

    operator fun invoke(): Arm = context.arm.copy(wrist = Joint(UP_ANGLE))
}
