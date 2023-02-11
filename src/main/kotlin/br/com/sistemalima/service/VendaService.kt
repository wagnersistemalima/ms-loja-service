package br.com.sistemalima.service

import br.com.sistemalima.dto.input.VendaInput
import br.com.sistemalima.dto.output.Veiculo
import br.com.sistemalima.exceptions.VeiculoException
import br.com.sistemalima.http.VeiculoHttp
import br.com.sistemalima.model.Observabilidade
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class VendaService(
    private val veiculoHttp: VeiculoHttp
) {

    private val logger = LoggerFactory.getLogger(VendaService::class.java)

    fun realizarVenda(vendaInput: VendaInput, observabilidade: Observabilidade): Veiculo {
        logger.info("Movimentação do processo request: $observabilidade")
        // comunicação entre microsserviços
        val idVeiculo = vendaInput.veiculo
        try {
            val veiculo = veiculoHttp.findById(idVeiculo)
            logger.info("Retorno do processo request: $observabilidade")
            return veiculo.get()
        }catch (e: Exception) {
            logger.error("Error: $observabilidade, message: ${e.message}")
            throw VeiculoException("Id do Veiculo nao encotrado na base")
        }

    }
}