package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.mechanics.Arm

abstract class Action {
    abstract operator fun invoke(clockWise: Boolean): Arm
}
