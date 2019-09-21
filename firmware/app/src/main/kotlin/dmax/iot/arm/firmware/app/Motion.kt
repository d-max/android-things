package dmax.iot.arm.firmware.app

import dmax.iot.arm.firmware.hardware.Hardware
import dmax.iot.arm.firmware.mechanics.Arm

class Motion(private val hardware: Hardware) {

    fun perform(arm: Arm) = with(hardware) {
        servo2.rotate(arm.wrist.angle)
        servo1.rotate(arm.elbow.angle)
        servo0.rotate(arm.base.angle)
    }
}
