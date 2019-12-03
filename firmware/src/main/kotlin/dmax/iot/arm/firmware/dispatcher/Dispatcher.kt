package dmax.iot.arm.firmware.dispatcher

import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.mechanics.Joint
import dmax.iot.arm.firmware.motion.Motion
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class Dispatcher(
    private val context: Context,
    private val motion: Motion
) {

    private val scope = MainScope()
    private val channel = Channel<Joint>()

    fun terminate() {
        scope.cancel()
    }

    fun dispatch() {
        scope.launch {
            for (joint in channel) {
                motion.rotate(joint)
                context.update(joint)
            }
        }
    }

    fun post(joint: Joint) {
        scope.launch {
            channel.send(joint)
        }
    }
}

private fun Context.update(joint: Joint) {
    arm = when(joint) {
        is Joint.Base -> arm.copy(base = joint)
        is Joint.Elbow -> arm.copy(elbow = joint)
        is Joint.Wrist -> arm.copy(wrist = joint)
    }
}