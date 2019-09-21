package dmax.iot.arm.firmware.mechanics

sealed class Joint(open val angle: Int) {
    data class Base(override val angle: Int) : Joint(angle)
    data class Elbow(override val angle: Int) : Joint(angle)
    data class Wrist(override val angle: Int) : Joint(angle)
}
