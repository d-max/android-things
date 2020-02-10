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
    zeroAnglePwmValue: Int = PCA9685.MIN_PWM_VALUE,
    maxAnglePwmValue: Int = PCA9685.MAX_PWM_VALUE,
    private val channel: Int,
    private val pca9685: PCA9685
) : Servo(zeroAnglePwmValue, maxAnglePwmValue) {

    override fun setPwmValue(value: Int) = pca9685.setPwmValue(channel, value)
}

class PwmServo(
    private val pwm: Pwm
) : Servo(PWD_MIN, PWM_MAX) {

    companion object {
        // micro seconds
        private const val PWD_MIN = 750
        private const val PWM_MAX = 2400
        private const val PULSE_PERIOD_MS = 20000.0 // 1000000/50Hz
    }

    override fun setPwmValue(value: Int) {
        val dutyCycleInPercents = value * 100 / PULSE_PERIOD_MS
        pwm.setPwmDutyCycle(dutyCycleInPercents)
    }
}
