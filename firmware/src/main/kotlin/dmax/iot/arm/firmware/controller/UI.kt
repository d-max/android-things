package dmax.iot.arm.firmware.controller

import android.view.View
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import dmax.iot.arm.firmware.R
import dmax.iot.arm.firmware.app.Context
import dmax.iot.arm.firmware.dispatcher.Dispatcher
import dmax.iot.arm.firmware.operations.ANGLE_MAX
import dmax.iot.arm.firmware.operations.ANGLE_MIN
import dmax.iot.arm.firmware.operations.Operation
import dmax.iot.arm.firmware.operations.rotateAngleBase
import dmax.iot.arm.firmware.operations.rotateAngleElbow
import dmax.iot.arm.firmware.operations.rotateAngleWrist

private typealias Rotation = (Int) -> Operation

class UiController(
    private val context: Context,
    private val dispatcher: Dispatcher
) {

    fun init(view: View) {

        val toggleGroup by lazy { view.findViewById<MaterialButtonToggleGroup>(R.id.selector) }
        val buttonMin by lazy { view.findViewById<MaterialButton>(R.id.button_min) }
        val buttonMax by lazy { view.findViewById<MaterialButton>(R.id.button_max) }
        val seekBar by lazy { view.findViewById<AppCompatSeekBar>(R.id.seekbar_value) }

        var factory: (Rotation)? = null

        buttonMin.setOnClickListener {
            seekBar.progress = ANGLE_MIN
            val operation = factory?.invoke(ANGLE_MIN)
            operation?.invoke(dispatcher)
        }

        buttonMax.setOnClickListener {
            seekBar.progress = ANGLE_MAX
            val operation = factory?.invoke(ANGLE_MAX)
            operation?.invoke(dispatcher)
        }

        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                factory = when (checkedId) {
                    R.id.button_base -> { value -> context.rotateAngleBase(value) }
                    R.id.button_elbow -> { value -> context.rotateAngleElbow(value) }
                    R.id.button_wrist -> { value -> context.rotateAngleWrist(value) }
                    else -> error("Should never happen")
                }
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val operation = factory?.invoke(progress)
                    operation?.invoke(dispatcher)
                }
            }
        })
    }
}
