package rs.rocketbyte.wifisilencer.core.usecase

import rs.rocketbyte.wifisilencer.data.repository.Repository


internal class DefaultUseCase(
    private val repository: Repository
) : UseCase