package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.mechanics.Joint

class Move1(private val context: Context) {

    operator fun invoke() = context.arm.copy(
        base = Joint(60),
        elbow = Joint(90)
    )
}
