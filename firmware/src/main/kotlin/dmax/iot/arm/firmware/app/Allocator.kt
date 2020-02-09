package dmax.iot.arm.firmware.app

import dmax.iot.arm.firmware.controller.PadController
import dmax.iot.arm.firmware.controller.UiController
import dmax.iot.arm.firmware.dispatcher.Dispatcher
import dmax.iot.arm.firmware.hardware.Hardware
import dmax.iot.arm.firmware.motion.Motion

class Allocator() {
    val context by lazy { Context() }
    val hardware by lazy { Hardware() }
    val motion by lazy { Motion(hardware) }
    val dispatcher by lazy { Dispatcher(context, motion) }
    val padController by lazy { PadController(context, dispatcher) }
    val uiController by lazy { UiController(context, dispatcher) }
}
