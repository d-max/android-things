package dmax.iot.arm.firmware.mechanics

data class Arm(
    val femur: Bone,
    val tibia: Bone,
    val fibula: Bone,
    val base: Joint.Base,
    val elbow: Joint.Elbow,
    val wrist: Joint.Wrist
)
