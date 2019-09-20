package dmax.iot.arm.firmware.app

import android.app.Activity

class Main : Activity() {

    private val logic = GlueLogic()

    override fun onStart() {
        super.onStart()
        logic.start()
    }

    override fun onStop() {
        logic.stop()
        super.onStop()
    }
}
