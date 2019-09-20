package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.mechanics.Arm
import dmax.iot.arm.firmware.mechanics.Joint

class PointerDown(private val context: Context) {

    companion object {
        private const val DOWN_ANGLE = 90
    }

    operator fun invoke(): Arm = context.arm.copy(wrist = Joint(DOWN_ANGLE))
}
