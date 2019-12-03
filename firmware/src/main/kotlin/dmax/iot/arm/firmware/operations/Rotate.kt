package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.dispatcher.Dispatcher
import dmax.iot.arm.firmware.mechanics.Joint
import dmax.iot.arm.firmware.mechanics.copy

fun Context.rotateBase(clockWise: Boolean): Operation = Rotate(arm.base, clockWise)
fun Context.rotateElbow(clockWise: Boolean): Operation = Rotate(arm.elbow, clockWise)
fun Context.rotateWrist(clockWise: Boolean): Operation = Rotate(arm.wrist, clockWise, 50, 90)

private class Rotate(
    private val joint: Joint,
    private val clockWise: Boolean,
    private val min: Int = 0,
    private val max: Int = 180
): Operation {

    override operator fun invoke(dispatcher: Dispatcher) {
        val angle = if (clockWise) max else min
        val joint = joint.copy(angle)
        dispatcher.post(joint)
    }
}
