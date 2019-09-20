package dmax.iot.arm.firmware.hardware

import com.google.android.things.pio.PeripheralManager
import dmax.iot.arm.firmware.hardware.PCA9685.Companion.CHANNEL_0
import dmax.iot.arm.firmware.hardware.PCA9685.Companion.CHANNEL_1
import dmax.iot.arm.firmware.hardware.PCA9685.Companion.CHANNEL_2

class Hardware {

    companion object {
        private const val PCA9685_ADDRESS: Int = 0x40
    }

    private val peripheral by lazy {
        PeripheralManager.getInstance() ?: throw IllegalStateException("Can't obtain peripheral manager")
    }

    private val pca9685: PCA9685 by lazy {
        val i2c = peripheral.i2cBusList.firstOrNull() ?: throw IllegalStateException("No I2C devices found")
        val device = peripheral.openI2cDevice(i2c, PCA9685_ADDRESS)
        PCA9685(device).apply {
            reset()
            setPwmFrequency(50)
        }
    }

    val servo0 by lazy {
        Servo(
            zeroAnglePwmValue = PCA9685.MIN_PWM_VALUE,
            maxAnglePwmValue = PCA9685.MAX_PWM_VALUE,
            channel = CHANNEL_0,
            pca9685 = pca9685
        )
    }

    val servo1 by lazy {
        Servo(
            zeroAnglePwmValue = PCA9685.MIN_PWM_VALUE,
            maxAnglePwmValue = PCA9685.MAX_PWM_VALUE,
            channel = CHANNEL_1,
            pca9685 = pca9685
        )
    }

    val servo2 by lazy {
        Servo(
            zeroAnglePwmValue = PCA9685.MIN_PWM_VALUE,
            maxAnglePwmValue = PCA9685.MAX_PWM_VALUE,
            channel = CHANNEL_2,
            pca9685 = pca9685
        )
    }

    fun shutDown() {
        pca9685.close()
    }
}
