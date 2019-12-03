package dmax.iot.arm.firmware.operations

import dmax.iot.arm.firmware.dispatcher.Dispatcher

interface Operation {
    operator fun invoke(dispatcher: Dispatcher)
}