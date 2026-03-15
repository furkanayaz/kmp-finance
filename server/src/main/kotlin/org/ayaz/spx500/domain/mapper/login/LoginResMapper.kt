package org.ayaz.spx500.domain.mapper.login

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.ayaz.spx500.data.dto_s.auth.LoginResDTO
import org.ayaz.spx500.domain.models.user.UserModel
import org.ayaz.spx500.domain.util.Mapper

class LoginResMapper: Mapper<UserModel, LoginResDTO> {
    override fun invoke(dto: UserModel): LoginResDTO = LoginResDTO(dto.fistName, dto.lastName, "", dto.createdAt.toLocalDateTime(TimeZone.currentSystemDefault()))
}