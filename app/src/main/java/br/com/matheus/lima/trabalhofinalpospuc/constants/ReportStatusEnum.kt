package br.com.matheus.lima.trabalhofinalpospuc.constants

enum class ReportStatusEnum(
    val type: String
) {
    FINISHED("finished"),
    PENDING("pending"),
    DENIED("denied"),
    IN_PROGRESS("in_progress");

    companion object {
        fun toEnum(status: String): ReportStatusEnum {
            return when (status) {
                PENDING.type -> PENDING
                IN_PROGRESS.type -> IN_PROGRESS
                FINISHED.type -> FINISHED
                DENIED.type -> DENIED
                else -> PENDING
            }
        }
    }
}
