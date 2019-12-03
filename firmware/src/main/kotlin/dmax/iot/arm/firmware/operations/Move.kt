package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.dispatcher.Dispatcher
import dmax.iot.arm.firmware.math.Condition
import dmax.iot.arm.firmware.math.calculateAngles
import dmax.iot.arm.firmware.mechanics.Arm
import dmax.iot.arm.firmware.mechanics.Joint

fun Context.move(x: Int, y: Int): Operation = Move(arm, x, y)

private class Move(
    private val arm: Arm,
    private val x: Int,
    private val y: Int
): Operation {

    override operator fun invoke(dispatcher: Dispatcher) {
        val (alpha, beta) = calculateAngles(
            Condition(
                x = x,
                y = y,
                a = arm.femur.length,
                b = arm.tibia.length
            )
        )
        dispatcher.post(
            Joint.Base(alpha)
        )
        dispatcher.post(
            Joint.Elbow(beta)
        )
    }
}
