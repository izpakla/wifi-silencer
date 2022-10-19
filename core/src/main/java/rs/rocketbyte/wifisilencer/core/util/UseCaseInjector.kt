package rs.rocketbyte.wifisilencer.core.util

import android.content.Context
import rs.rocketbyte.wifisilencer.core.usecase.DefaultUseCase
import rs.rocketbyte.wifisilencer.core.usecase.UseCase
import rs.rocketbyte.wifisilencer.data.util.RepositoryInjector

object UseCaseInjector {

    fun getUseCase(context: Context): UseCase {
        return DefaultUseCase(RepositoryInjector.getRepository(context))
    }

}