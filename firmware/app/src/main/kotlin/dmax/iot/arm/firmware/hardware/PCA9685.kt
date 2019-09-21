package dmax.iot.arm.firmware.hardware

import com.google.android.things.pio.I2cDevice
import kotlin.experimental.and
import kotlin.experimental.or

class PCA9685(private val i2c: I2cDevice) {

    companion object {

        const val MAX_PWM_VALUE = 450
        const val MIN_PWM_VALUE = 200

        const val CHANNEL_0 = 0
        const val CHANNEL_1 = 1
        const val CHANNEL_2 = 2

        private const val PCA9685_MODE1 = 0x0
        private const val PCA9685_PRESCALE = 0xFE
        private const val PCA9685_25Mhz_TICKS = 25000000f

        private const val LED0_ON_L = 0x6
        private const val LED0_ON_H = 0x7
        private const val LED0_OFF_L = 0x8
        private const val LED0_OFF_H = 0x9

        private const val LED1_ON_L = 0x0a
        private const val LED1_ON_H = 0x0b
        private const val LED1_OFF_L = 0x0c
        private const val LED1_OFF_H = 0x0d

        private const val LED2_ON_L = 0x0e
        private const val LED2_ON_H = 0x0f
        private const val LED2_OFF_L = 0x10
        private const val LED2_OFF_H = 0x11
    }

    fun close() {
        i2c.close()
    }

    fun reset() {
        val value: Byte = 0x08
        i2c.writeRegByte(PCA9685_MODE1, value)
    }

    fun setPwmFrequency(frequency: Byte) {
        val freq = frequency * 0.9f
        var prescale = PCA9685_25Mhz_TICKS / 4096f
        prescale /= freq
        prescale -= 1

        val oldMode = i2c.readRegByte(PCA9685_MODE1)
        val newMode = (oldMode and 0x7f) or 0x10 // sleep
        i2c.apply {
            writeRegByte(PCA9685_MODE1, newMode) // go to sleep
            writeRegByte(PCA9685_PRESCALE, prescale.toByte()) // set prescale
            writeRegByte(PCA9685_MODE1, oldMode)
            Thread.sleep(5)
            writeRegByte(PCA9685_MODE1, oldMode or 0xa0.toByte()) // autoincrement
        }
    }

    fun setPwmValue(channel: Int, value: Int) {
        Thread.sleep(50) // todo
        i2c.writeWord(channel, value)
    }

    private fun I2cDevice.writeWord(channel: Int, value: Int) {
        val register = when(channel) {
            CHANNEL_0 -> LED0_OFF_L
            CHANNEL_1 -> LED1_OFF_L
            CHANNEL_2 -> LED2_OFF_L
            else -> throw IllegalArgumentException("Channel doesn't exist")
        }
        writeRegWord(register, value.toShort())
    }

    private fun I2cDevice.writeBytes(channel: Int, value: Int) {
        val valueLow = value and 0xFF
        val valueHigh = value shr 8
        val (registerOffLow, registerOffHigh) = when(channel) {
            CHANNEL_0 -> LED0_OFF_L to LED0_OFF_H
            CHANNEL_1 -> LED1_OFF_L to LED1_OFF_H
            CHANNEL_2 -> LED2_OFF_L to LED2_OFF_H
            else -> throw IllegalArgumentException("Channel doesn't exist")
        }
        writeRegByte(registerOffLow, valueLow.toByte())
        writeRegByte(registerOffHigh, valueHigh.toByte())
    }


}
