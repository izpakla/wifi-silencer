package rs.rocketbyte.wifisilencer.core.usecase.dnd

interface DndUseCase {
    fun isDndPermissionGranted(): Boolean
}