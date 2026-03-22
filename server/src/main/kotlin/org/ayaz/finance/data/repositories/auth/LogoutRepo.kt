package org.ayaz.finance.data.repositories.auth

import org.ayaz.finance.data.sessions.token.TokenSession
import org.ayaz.finance.data.uow_s.user.IUserGetUuidUow
import org.ayaz.finance.data.base.Response

fun interface ILogoutRepo {
    operator fun invoke(email: String): Response<Boolean>
}

class LogoutRepo(
    private val userGetUuidUow: IUserGetUuidUow,
    private val tokenSession: TokenSession
): ILogoutRepo {
    override fun invoke(email: String): Response<Boolean> {
        val userUuid = userGetUuidUow(email) ?: return Response.Error(errorMessages = listOf("logout.error"))
        return if (tokenSession.removeToken(userUuid)) Response.Success() else Response.Error(errorMessages = listOf("logout.error"))
    }
}