package br.com.matheus.lima.trabalhofinalpospuc.data.mapper

import br.com.matheus.lima.trabalhofinalpospuc.base.BaseMapper
import br.com.matheus.lima.trabalhofinalpospuc.data.model.NewReportRequest
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.NewReport

object NewReportMapper : BaseMapper<NewReport, NewReportRequest>() {
    override fun transformTo(source: NewReport): NewReportRequest =
        NewReportRequest(
            source.cpf,
            source.title,
            source.description,
            source.latitude,
            source.longitude,
            source.photos,
            source.userId
        )
}