package org.ayaz.finance.data.repositories.auth

import org.ayaz.finance.data.dto_s.auth.LoginReqDTO
import org.ayaz.finance.data.dto_s.auth.LoginResDTO
import org.ayaz.finance.data.sessions.token.TokenSession
import org.ayaz.finance.data.uow_s.auth.ILoginUow
import org.ayaz.finance.data.base.Response
import org.ayaz.finance.data.auth.jwt.JWTUtil
import org.ayaz.finance.data.auth.jwt.JWTValues
import org.ayaz.finance.domain.mapper.login.LoginResMapper
import org.ayaz.finance.domain.models.user.UserModel
import org.ayaz.finance.domain.base.Resource

fun interface ILoginRepo {
    operator fun invoke(req: LoginReqDTO, jwtValues: JWTValues): Response<LoginResDTO>
}

class LoginRepo(
    private val loginUow: ILoginUow,
    private val jwtUtil: JWTUtil,
    private val tokenSession: TokenSession,
    private val loginResMapper: LoginResMapper
): ILoginRepo {
    override fun invoke(req: LoginReqDTO, jwtValues: JWTValues): Response<LoginResDTO> {
        return when(val response = loginUow(req)) {
            is Resource.Error<UserModel> -> Response.Error(errorMessages = response.messages)
            is Resource.Success<UserModel> -> {
                val token = jwtUtil.createToken(jwtValues, req.email)

                if (tokenSession.addToken(response.item.uuid, token)) {
                    val responseItem = loginResMapper(response.item).copy(token = token)
                    Response.Success(item = responseItem)
                } else {
                    Response.Error(code = 500, errorMessages = listOf("server.unknown.error"))
                }
            }
        }
    }
}