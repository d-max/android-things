package dmax.iot.arm.firmware.mechanics

data class Arm(
    val base: Joint,
    val femur: Bone,
    val elbow: Joint,
    val tibia: Bone,
    val wrist: Joint,
    val fibula: Bone
)
