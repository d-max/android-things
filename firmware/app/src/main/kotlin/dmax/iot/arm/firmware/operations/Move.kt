package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.math.Condition
import dmax.iot.arm.firmware.math.calculateAngles
import dmax.iot.arm.firmware.mechanics.Arm
import dmax.iot.arm.firmware.mechanics.Joint

class Move(private val context: Context) {

    operator fun invoke(x: Int, y: Int): Arm {
        val arm = context.arm
        val (alpha, beta) = calculateAngles(
            Condition(
                x = x,
                y = y,
                a = arm.femur.length,
                b = arm.tibia.length
            )
        )
        return arm.copy(
            base = Joint(alpha),
            elbow = Joint(beta)
        )
    }
}
