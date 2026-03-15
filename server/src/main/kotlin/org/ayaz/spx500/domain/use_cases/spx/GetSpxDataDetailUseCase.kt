package org.ayaz.spx500.domain.use_cases.spx

import org.ayaz.spx500.data.repositories.spx.ISpxDataRepo

class GetSpxDataDetailUseCase(
    private val repo: ISpxDataRepo
) {
    operator fun invoke(id: String) = repo.getDetailData(id)
}