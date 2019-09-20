package dmax.iot.arm.firmware.hardware

data class Servo(
    private val zeroAnglePwmValue: Int,
    private val maxAnglePwmValue: Int,
    private val channel: Int,
    val pca9685: PCA9685
) {
    private val oneDegree = (maxAnglePwmValue - zeroAnglePwmValue) / 180

    fun rotate(angle: Int) {
        if (angle !in 0..180) return
        val dutyCycle = zeroAnglePwmValue + angle * oneDegree
        pca9685.setPwmValue(channel, dutyCycle)
    }
}
