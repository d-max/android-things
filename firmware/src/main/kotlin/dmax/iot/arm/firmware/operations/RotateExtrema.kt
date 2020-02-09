package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.dispatcher.Dispatcher
import dmax.iot.arm.firmware.mechanics.Joint
import dmax.iot.arm.firmware.mechanics.copy

fun Context.rotateExtremaBase(clockWise: Boolean): Operation = RotateExtrema(arm.base, clockWise)
fun Context.rotateExtremaElbow(clockWise: Boolean): Operation = RotateExtrema(arm.elbow, clockWise)
fun Context.rotateExtremaWrist(clockWise: Boolean): Operation = RotateExtrema(arm.wrist, clockWise, ANGLE_QUARTER, ANGLE_MIDDLE)

private class RotateExtrema(
    private val joint: Joint,
    private val clockWise: Boolean,
    private val min: Int = ANGLE_MIN,
    private val max: Int = ANGLE_MAX
): Operation {

    override operator fun invoke(dispatcher: Dispatcher) {
        val angle = if (clockWise) max else min
        val joint = joint.copy(angle)
        dispatcher.post(joint)
    }
}
