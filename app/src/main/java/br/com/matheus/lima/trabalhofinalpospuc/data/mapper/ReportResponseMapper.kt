package br.com.matheus.lima.trabalhofinalpospuc.data.mapper

import br.com.matheus.lima.trabalhofinalpospuc.base.BaseMapper
import br.com.matheus.lima.trabalhofinalpospuc.constants.ReportStatusEnum
import br.com.matheus.lima.trabalhofinalpospuc.data.model.ReportResponse
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.MyReport

object ReportResponseMapper : BaseMapper<ReportResponse, MyReport>() {
    override fun transformTo(source: ReportResponse): MyReport =
        MyReport(
            source.id,
            ReportStatusEnum.toEnum(source.status),
            source.title,
            source.description,
            source.cpf ?: "",
            source.latitude.toDoubleOrNull() ?: 0.0,
            source.longitude.toDoubleOrNull() ?: 0.0,
            source.photos ?: listOf()
        )
}
