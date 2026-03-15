package org.ayaz.spx500.domain.di

import org.ayaz.spx500.domain.mapper.login.LoginResMapper
import org.ayaz.spx500.domain.mapper.spx.SpxDetailResMapper
import org.ayaz.spx500.domain.mapper.spx.SpxResMapper
import org.ayaz.spx500.domain.mapper.user.UserMapper
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class MapperModule {

    @Single
    fun provideUserMapper(): UserMapper = UserMapper()

    @Single
    fun provideLoginResMapper(): LoginResMapper = LoginResMapper()

    @Single
    fun provideSpxResMapper(): SpxResMapper = SpxResMapper()

    @Single
    fun provideSpxDetailResMapper(): SpxDetailResMapper = SpxDetailResMapper()

}