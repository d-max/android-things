package dmax.iot.arm.firmware.hardware

import com.google.android.things.pio.Pwm

abstract class Servo(
    private val zeroAnglePwmValue: Int,
    private val maxAnglePwmValue: Int
) {
    private val oneDegree = (maxAnglePwmValue - zeroAnglePwmValue) / 180

    fun rotate(angle: Int) {
        if (angle !in 0..180) return
        val dutyCycle = zeroAnglePwmValue + angle * oneDegree
        setPwmValue(dutyCycle)
    }

    abstract fun setPwmValue(value: Int)
}

class I2cServo(
    zeroAnglePwmValue: Int,
    maxAnglePwmValue: Int,
    private val channel: Int,
    private val pca9685: PCA9685
) : Servo(zeroAnglePwmValue, maxAnglePwmValue) {

    override fun setPwmValue(value: Int) = pca9685.setPwmValue(channel, value)
}

class PwmServo(
    zeroAnglePwmValue: Int,
    maxAnglePwmValue: Int,
    private val pwm: Pwm
) : Servo(zeroAnglePwmValue, maxAnglePwmValue) {

    override fun setPwmValue(value: Int) = pwm.setPwmDutyCycle(value.toDouble())
}
