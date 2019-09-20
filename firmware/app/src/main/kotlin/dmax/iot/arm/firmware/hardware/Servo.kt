package dmax.iot.arm.firmware.hardware

data class Servo(
    private val zeroAnglePwmValue: Int,
    private val maxAnglePwmValue: Int,
    private val channel: Int,
    val pca9685: PCA9685
) {

    fun rotate(angle: Int) {
        val value = when (angle) {
            0 -> zeroAnglePwmValue
            90 -> maxAnglePwmValue
            else -> return
        }
        pca9685.setPwmValue(channel, value)
    }
}
