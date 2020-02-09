package dmax.iot.arm.firmware.motion

import dmax.iot.arm.firmware.hardware.Hardware
import dmax.iot.arm.firmware.mechanics.Joint

class Motion(private val hardware: Hardware) {

    fun rotate(joint: Joint) = with(hardware) {
        when (joint) {
            is Joint.Base -> servoPwm0 //servoI2c2
            is Joint.Elbow -> servoPwm0 //servoI2c1
            is Joint.Wrist -> servoPwm0 //servoI2c0
        }.rotate(joint.angle)
    }
}
