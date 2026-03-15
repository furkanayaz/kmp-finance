package org.ayaz.spx500.domain.use_cases.spx

import org.ayaz.spx500.data.repositories.spx.ISpxDataRepo

class GetSpxDataUseCase(
    private val repo: ISpxDataRepo
) {
    operator fun invoke(pageNo: Int, pageSize: Int) = repo.getData(pageNo, pageSize)
}