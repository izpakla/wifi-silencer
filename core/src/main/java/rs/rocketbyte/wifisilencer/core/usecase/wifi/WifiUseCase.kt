package rs.rocketbyte.wifisilencer.core.usecase.wifi

interface WifiUseCase {
    fun getCurrentSsid(): String?
    fun isConnectedToWifi(): Boolean
}