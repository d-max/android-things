package com.tooploox.iot.firmware.activities

import android.os.Looper
import com.tooploox.iot.firmware.Blinker
import com.tooploox.iot.firmware.PIN40
import com.tooploox.iot.firmware.drivers.LedController

/**
 * Created by mdy on 3/22/17.
 */
open class LedBlinkingActivity : IotActivity() {

    private lateinit var led: LedController

    override fun initPeriphery() {
        led = LedController(peripheralService, PIN40)
    }

    override fun closePeriphery() {
        led.close()
    }

    override fun doAction() {
        val blinker = Blinker(led, Looper.getMainLooper())
        blinker.scheduleCode(Blinker.CODE_ON)
    }
}