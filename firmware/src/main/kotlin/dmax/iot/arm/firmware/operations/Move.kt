package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.math.Condition
import dmax.iot.arm.firmware.math.calculateAngles
import dmax.iot.arm.firmware.mechanics.Joint
import dmax.iot.arm.firmware.motion.Motion

class Move(
    private val context: Context,
    private val motion: Motion
) {

    operator fun invoke(x: Int, y: Int) {
        val arm = context.arm
        val (alpha, beta) = calculateAngles(
            Condition(
                x = x,
                y = y,
                a = arm.femur.length,
                b = arm.tibia.length
            )
        )
        motion.rotate(Joint.Base(alpha))
        motion.rotate(Joint.Elbow(beta))
    }
}
