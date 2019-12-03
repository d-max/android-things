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

    protected abstract val angle: Int
    protected abstract fun createJoint(angle: Int) : Joint

    open operator fun invoke(clockWise: Boolean) {
        val angle = angle + STEP * (if (clockWise) 1 else -1)
        val joint = createJoint(angle)
        motion.rotate(joint)
    }
}

class RotateBase(private val context: Context, motion: Motion) : Rotate(motion) {

    override val angle: Int
        get() = context.arm.base.angle

    override fun createJoint(angle: Int) = Joint.Base(angle).also {
        context.arm = context.arm.copy(base = it)
    }
}

class RotateElbow(private val context: Context, motion: Motion) : Rotate(motion) {

    override val angle: Int
        get() = context.arm.elbow.angle

    override fun createJoint(angle: Int) = Joint.Elbow(angle).also {
        context.arm = context.arm.copy(elbow = it)
    }
}

class RotateWrist(private val context: Context, private val motion: Motion) : Rotate(motion) {

    override val angle: Int
        get() = context.arm.wrist.angle

    override fun createJoint(angle: Int) = Joint.Wrist(angle).also {
        context.arm = context.arm.copy(wrist = it)
    }

    override fun invoke(clockWise: Boolean) {
        val angle = if (clockWise) 50 else 90
        val joint = createJoint(angle)
        motion.rotate(joint)
    }

}
