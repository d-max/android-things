package com.tooploox.iot.firmware.activities

import com.tooploox.iot.firmware.PWM0
import com.tooploox.iot.firmware.drivers.ServoController

/**
 * Created by mdy on 3/22/17.
 */
open class ServoActivity : IotActivity() {

    private lateinit var servo: ServoController

    override fun initPeriphery() {
        servo = ServoController(peripheralService, PWM0)
    }

    override fun closePeriphery() {
        servo.close()
    }

    override fun doAction() {
        servo.on()
        servo.setAngle(0)
    }
}