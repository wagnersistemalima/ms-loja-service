package br.com.sistemalima.dto.input

import java.math.BigDecimal

data class VendaInput(
    val cliente: String,
    val veiculo: Long,
    val valor: BigDecimal,
    val quantidadeParcelas: Int
)
