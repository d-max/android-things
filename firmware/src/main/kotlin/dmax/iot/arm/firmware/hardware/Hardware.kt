package dmax.iot.arm.firmware.hardware

import com.google.android.things.pio.PeripheralManager
import com.google.android.things.pio.Pwm
import dmax.iot.arm.firmware.hardware.PCA9685.Companion.CHANNEL_0
import dmax.iot.arm.firmware.hardware.PCA9685.Companion.CHANNEL_1
import dmax.iot.arm.firmware.hardware.PCA9685.Companion.CHANNEL_2

class Hardware {

    companion object {
        private const val PCA9685_ADDRESS: Int = 0x40
    }

    private val peripheral by lazy {
        PeripheralManager.getInstance() ?: error("Can't obtain peripheral manager")
    }

    private val pca9685: PCA9685 by lazy {
        val i2c = peripheral.i2cBusList.firstOrNull() ?: error("No I2C devices found")
        val device = peripheral.openI2cDevice(i2c, PCA9685_ADDRESS)
        PCA9685(device)
    }

    private val pwm: Pwm by lazy {
        val pwm = peripheral.pwmList.firstOrNull() ?: error("PWM not found")
        peripheral.openPwm(pwm).apply {
            setEnabled(true)
            setPwmFrequencyHz(50.0)
        }
    }

    val servoI2c0: Servo by lazy {
        I2cServo(
            zeroAnglePwmValue = PCA9685.MIN_PWM_VALUE,
            maxAnglePwmValue = PCA9685.MAX_PWM_VALUE,
            channel = CHANNEL_0,
            pca9685 = pca9685
        )
    }

    val servoI2c1: Servo by lazy {
        I2cServo(
            zeroAnglePwmValue = PCA9685.MIN_PWM_VALUE + 50,
            maxAnglePwmValue = PCA9685.MAX_PWM_VALUE,
            channel = CHANNEL_1,
            pca9685 = pca9685
        )
    }

    val servoI2c2: Servo by lazy {
        I2cServo(
            zeroAnglePwmValue = PCA9685.MIN_PWM_VALUE,
            maxAnglePwmValue = PCA9685.MAX_PWM_VALUE,
            channel = CHANNEL_2,
            pca9685 = pca9685
        )
    }

    val servoPwm0: Servo by lazy {
        PwmServo(
            zeroAnglePwmValue = PCA9685.MIN_PWM_VALUE,
            maxAnglePwmValue = PCA9685.MAX_PWM_VALUE,
            pwm = pwm
        )
    }

    fun shutDown() {
        pca9685.close()
    }

    fun reset() {
        with(pca9685) {
            reset()
            setPwmFrequency(50)
        }
    }
}
