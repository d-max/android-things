package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.mechanics.Joint

class Move0(private val context: Context) {

    operator fun invoke() = context.arm.copy(
        base = Joint(0),
        elbow = Joint(0)
    )
}
