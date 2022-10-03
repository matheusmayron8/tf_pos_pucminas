package br.com.matheus.lima.trabalhofinalpospuc.base.extension

fun String.clearSpecialCharacters(): String {
    val pattern = Regex("[^A-ZÀ-Úa-zà-ú0-9 ]")
    return this.replace(pattern, "").trim()
}
