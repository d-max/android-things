package dmax.iot.arm.firmware.app

import android.app.Activity
import android.os.Bundle
import dmax.iot.arm.firmware.hardware.Hardware

class Main : Activity() {

    private val hardware = Hardware()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hardware.servo0.rotate(0)
    }

    override fun onDestroy() {
        hardware.shutDown()
        super.onDestroy()
    }
}
