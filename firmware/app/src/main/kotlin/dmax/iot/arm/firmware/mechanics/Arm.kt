package dmax.iot.arm.firmware.mechanics

data class Arm(
    val femur: Bone,
    val tibia: Bone,
    val fibula: Bone,
    var base: Joint.Base,
    var elbow: Joint.Elbow,
    var wrist: Joint.Wrist
)
