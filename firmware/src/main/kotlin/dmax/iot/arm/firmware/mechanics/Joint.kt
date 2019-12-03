package dmax.iot.arm.firmware.mechanics

sealed class Joint(open val angle: Int) {
    data class Base(override val angle: Int) : Joint(angle)
    data class Elbow(override val angle: Int) : Joint(angle)
    data class Wrist(override val angle: Int) : Joint(angle)
}

fun Joint.copy(angle: Int) = when (this) {
    is Joint.Base -> Joint.Base(angle)
    is Joint.Elbow -> Joint.Elbow(angle)
    is Joint.Wrist -> Joint.Wrist(angle)
}