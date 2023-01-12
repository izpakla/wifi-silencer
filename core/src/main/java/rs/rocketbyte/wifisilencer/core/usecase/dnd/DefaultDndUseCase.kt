package rs.rocketbyte.wifisilencer.core.usecase.dnd

import android.content.Context
import rs.rocketbyte.wifisilencer.core.commons.ext.isDndPermissionGranted

internal class DefaultDndUseCase(private val context: Context) : DndUseCase {

    override fun isDndPermissionGranted(): Boolean = context.isDndPermissionGranted()

}