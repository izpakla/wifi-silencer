package rs.rocketbyte.wifisilencer.core.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rs.rocketbyte.wifisilencer.core.manager.WifiConnectionListener
import rs.rocketbyte.wifisilencer.core.manager.WifiSilencer
import rs.rocketbyte.wifisilencer.core.manager.WifiSilencerManager
import rs.rocketbyte.wifisilencer.core.usecase.dnd.DefaultDndUseCase
import rs.rocketbyte.wifisilencer.core.usecase.dnd.DndUseCase
import rs.rocketbyte.wifisilencer.core.usecase.ringer.DefaultRingerUseCase
import rs.rocketbyte.wifisilencer.core.usecase.ringer.RingerUseCase
import rs.rocketbyte.wifisilencer.core.usecase.wifi.DefaultWifiUseCase
import rs.rocketbyte.wifisilencer.core.usecase.wifi.WifiUseCase
import rs.rocketbyte.wifisilencer.data.repository.Repository
import rs.rocketbyte.wifisilencer.data.util.RepositoryInjector

@Module
@InstallIn(SingletonComponent::class)
internal object RingerWifiModule {

    @Provides
    fun provideRepository(application: Application): Repository =
        RepositoryInjector.getRepository(application)

    @Provides
    fun provideWifiUseCase(application: Application): WifiUseCase =
        DefaultWifiUseCase(application)

    @Provides
    fun provideRingerUseCase(application: Application): RingerUseCase =
        DefaultRingerUseCase(application)

    @Provides
    fun provideDndUseCase(application: Application): DndUseCase =
        DefaultDndUseCase(application)

    @Provides
    fun provideWifiSilencer(wifiSilencerManager: WifiSilencerManager): WifiSilencer =
        wifiSilencerManager

    @Provides
    fun provideWifiConnectionListener(wifiSilencerManager: WifiSilencerManager): WifiConnectionListener =
        wifiSilencerManager

}