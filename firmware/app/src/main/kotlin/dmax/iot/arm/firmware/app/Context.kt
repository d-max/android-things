package dmax.iot.arm.firmware.app

import dmax.iot.arm.firmware.mechanics.Arm
import dmax.iot.arm.firmware.mechanics.Bone
import dmax.iot.arm.firmware.mechanics.Joint

class Context {

    var arm: Arm = Arm(
        base = Joint.Base(0),
        femur = Bone(2),
        elbow = Joint.Elbow(0),
        tibia = Bone(3),
        wrist = Joint.Wrist(0),
        fibula = Bone(1)
    )
}
