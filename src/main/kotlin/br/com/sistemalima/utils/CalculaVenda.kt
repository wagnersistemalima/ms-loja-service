package br.com.sistemalima.utils

import br.com.sistemalima.dto.input.VendaInput
import br.com.sistemalima.dto.output.Parcela
import br.com.sistemalima.dto.output.Veiculo
import br.com.sistemalima.dto.output.Venda
import java.math.RoundingMode
import java.time.LocalDate

class CalculaVenda {

    companion object {

        private val tag = "class: calculaVenda"
        private val tagCalcularVenda = "method: calcularVenda"

        fun calcularVenda(vendaInput: VendaInput, veiculo: Veiculo): Venda {
            // regra para calcular
            var parcelas: List<Parcela> = ArrayList()
            // onde 2 é a escala e RoundingMode.HALF_UP é o modo de arredondamento
            var valorParcela = vendaInput.valor.divide(vendaInput.quantidadeParcelas.toBigDecimal(), 2,  RoundingMode.HALF_UP)
            var dataVencimentoParcela = LocalDate.now().plusMonths(1) // primeira parcela

            // gerar parcelamneto

            for (i in 1..vendaInput.quantidadeParcelas) {
                val parcela = Parcela(valorParcela, dataVencimentoParcela.toString())
                parcelas = parcelas.plus(parcela)
                dataVencimentoParcela = dataVencimentoParcela.plusMonths(1) // gerando proximas parcelas
            }

            return Venda(vendaInput.cliente, veiculo, vendaInput.valor, parcelas)
        }
    }
}