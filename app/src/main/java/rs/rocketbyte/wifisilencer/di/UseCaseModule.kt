package rs.rocketbyte.wifisilencer.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rs.rocketbyte.wifisilencer.core.usecase.UseCase
import rs.rocketbyte.wifisilencer.core.util.UseCaseInjector
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideUseCase(
        @ApplicationContext context: Context
    ): UseCase = UseCaseInjector.getUseCase(context)

}