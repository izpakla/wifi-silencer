package rs.rocketbyte.wifisilencer.core.manager

interface WifiConnectionListener {
    fun onConnectionChanged(isConnected: Boolean)
}