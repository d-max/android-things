package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.mechanics.Joint

class Rotate(private val context: Context) : Action() {

    override operator fun invoke(clockWise: Boolean) = context.arm.let {
        it.copy(
            base = Joint(it.base.angle + 10 * (if (clockWise) 1 else -1))
        )
    }
}
