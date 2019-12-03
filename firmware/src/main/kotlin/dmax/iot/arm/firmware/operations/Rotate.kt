package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.mechanics.Joint
import dmax.iot.arm.firmware.motion.Motion

abstract class Rotate(
    private val motion: Motion
) {

    companion object {
        private const val STEP = 10
    }

    protected abstract val joint: Joint
    protected abstract fun createJoint(angle: Int) : Joint

    open operator fun invoke(clockWise: Boolean) {
        val angle = joint.angle + STEP * (if (clockWise) 1 else -1)
        motion.rotate(createJoint(angle))
    }
}

class RotateBase(private val context: Context, motion: Motion) : Rotate(motion) {
    override val joint: Joint
        get() = context.arm.base

    override fun createJoint(angle: Int) =
        Joint.Base(angle).apply { context.arm.base = this }
}

class RotateElbow(private val context: Context, motion: Motion) : Rotate(motion) {
    override val joint: Joint
        get() = context.arm.elbow

    override fun createJoint(angle: Int)
        = Joint.Elbow(angle).apply { context.arm.elbow = this }
}

class RotateWrist(private val context: Context, private val motion: Motion) : Rotate(motion) {
    override val joint: Joint
        get() = context.arm.wrist

    override fun createJoint(angle: Int)
        = Joint.Wrist(angle).apply { context.arm.wrist = this }

    override fun invoke(clockWise: Boolean) {
        val angle = if (clockWise) 50 else 90
        motion.rotate(createJoint(angle))
    }

}
