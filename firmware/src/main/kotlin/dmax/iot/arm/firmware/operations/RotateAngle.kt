package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.dispatcher.Dispatcher
import dmax.iot.arm.firmware.mechanics.Joint
import dmax.iot.arm.firmware.mechanics.copy

fun Context.rotateAngleBase(angle: Int): Operation = RotateAngle(arm.base, angle)
fun Context.rotateAngleElbow(angle: Int): Operation = RotateAngle(arm.elbow, angle)
fun Context.rotateAngleWrist(angle: Int): Operation = RotateAngle(arm.wrist, angle)

private class RotateAngle(
    private val joint: Joint,
    private val angle: Int
): Operation {

    override operator fun invoke(dispatcher: Dispatcher) {
        val joint = joint.copy(angle)
        dispatcher.post(joint)
    }
}
