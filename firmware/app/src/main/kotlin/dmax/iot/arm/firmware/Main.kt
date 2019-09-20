package dmax.iot.arm.firmware

import android.app.Activity
import android.os.Bundle
import com.google.android.things.pio.I2cDevice
import com.google.android.things.pio.PeripheralManager
import dmax.iot.arm.firmware.hardware.PCA9685

private const val I2C_ADDRESS: Int = 0x40

open class Main : Activity() {

    private companion object {
        /*
        min servo time = 500 us
        max servo time = 2400 us
        ticks per second = 4096 * 50 = 204800
        min ticks = 204800 / 1000000 * 500 = 103
        max ticks = 204800 / 1000000 * 2400 = 492
        one degree time = (492 - 103) / 180 = ~2
        */

        private const val MIN_TIME = 103
        private const val MAX_TIME = 492
        private const val ONE_DEGREE_TIME = 2
    }

    private val peripheral by lazy {
        PeripheralManager.getInstance() ?: throw IllegalStateException("Can't obtain peripheral manager")
    }

    private val i2c: I2cDevice by lazy {
        val i2c = peripheral.i2cBusList.firstOrNull() ?: throw IllegalStateException("No I2C devices found")
        peripheral.openI2cDevice(i2c, I2C_ADDRESS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PCA9685(i2c).apply {
            reset()
            setPwmFrequency(50)

//                start()
            min()
//                max()
//                middle()
        }
    }

    override fun onDestroy() {
        i2c.close()
        super.onDestroy()
    }
}
