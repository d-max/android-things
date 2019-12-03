package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.dispatcher.Dispatcher
import dmax.iot.arm.firmware.mechanics.Joint
import dmax.iot.arm.firmware.mechanics.copy

fun Context.rotateStepBase(clockWise: Boolean): Operation = RotateStep(arm.base, clockWise)
fun Context.rotateStepElbow(clockWise: Boolean): Operation = RotateStep(arm.elbow, clockWise)
fun Context.rotateStepWrist(clockWise: Boolean): Operation = RotateStep(arm.wrist, clockWise)

private class RotateStep(
    private val joint: Joint,
    private val clockWise: Boolean
): Operation {

    companion object {
        private const val STEP = 10
    }

    override operator fun invoke(dispatcher: Dispatcher) {
        val angle = joint.angle + STEP * (if (clockWise) 1 else -1)
        val joint = joint.copy(angle)
        dispatcher.post(joint)
    }
}
