package br.com.matheus.lima.trabalhofinalpospuc.base.validators

import android.content.Context
import br.com.matheus.lima.trabalhofinalpospuc.base.extension.clearSpecialCharacters
import java.util.regex.Pattern

object CPFValidator {

    const val CPF_LENGTH = 11
    private val mPattern = Pattern.compile("^([0-9]{11})$")

    fun validate(text: String, context: Context) {
        val cleanText = text.clearSpecialCharacters()

        if (!mPattern.matcher(cleanText).matches()) {
            throw ValidatorException("CPF inválido")
        }

        val numbers = arrayListOf<Int>()

        cleanText.filter { it.isDigit() }.forEach {
            numbers.add(it.toString().toInt())
        }

        for (n in 0 until CPF_LENGTH - 1) {
            val digits = arrayListOf<Int>()
            repeat((0 until CPF_LENGTH).count()) { digits.add(n) }

            if (numbers == digits) throw ValidatorException("CPF inválido")
        }

        val dv1 = (
            (0 until CPF_LENGTH - 2).sumOf {
                (it + 1) * numbers[it]
            }
            ).rem(CPF_LENGTH).let {
            if (it >= CPF_LENGTH - 1) 0 else it
        }

        val dv2 = (
            (0 until CPF_LENGTH - 2).sumOf {
                it * numbers[it]
            }.let {
                (it + (dv1 * (CPF_LENGTH - 2))).rem(CPF_LENGTH)
            }
            ).let {
            if (it >= CPF_LENGTH - 1) 0 else it
        }

        if (numbers[CPF_LENGTH - 2] != dv1 || numbers[CPF_LENGTH - 1] != dv2) {
            throw ValidatorException("CPF inválido")
        }
    }

    fun validate(text: String): Boolean {
        val cleanText = text.clearSpecialCharacters()

        if (!mPattern.matcher(cleanText).matches()) {
            return false
        }

        val numbers = arrayListOf<Int>()

        cleanText.filter { it.isDigit() }.forEach {
            numbers.add(it.toString().toInt())
        }

        for (n in 0 until CPF_LENGTH - 1) {
            val digits = arrayListOf<Int>()
            repeat((0 until CPF_LENGTH).count()) { digits.add(n) }

            if (numbers == digits) return false
        }

        val dv1 = (
            (0 until CPF_LENGTH - 2).sumOf {
                (it + 1) * numbers[it]
            }
            ).rem(CPF_LENGTH).let {
            if (it >= CPF_LENGTH - 1) 0 else it
        }

        val dv2 = (
            (0 until CPF_LENGTH - 2).sumOf {
                it * numbers[it]
            }.let {
                (it + (dv1 * (CPF_LENGTH - 2))).rem(CPF_LENGTH)
            }
            ).let {
            if (it >= CPF_LENGTH - 1) 0 else it
        }

        if (numbers[CPF_LENGTH - 2] != dv1 || numbers[CPF_LENGTH - 1] != dv2) {
            return false
        }
        return true
    }
}
