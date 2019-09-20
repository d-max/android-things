package dmax.iot.arm.firmware.app

import dmax.iot.arm.firmware.mechanics.Arm
import dmax.iot.arm.firmware.mechanics.Bone
import dmax.iot.arm.firmware.mechanics.Joint

class Context {

    var arm: Arm = Arm(
        base = Joint(0),
        femur = Bone(2),
        elbow = Joint(0),
        tibia = Bone(3),
        wrist = Joint(0),
        fibula = Bone(1)
    )
}
