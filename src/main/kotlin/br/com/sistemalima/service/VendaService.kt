package br.com.sistemalima.service


import br.com.sistemalima.dto.input.VendaInput
import br.com.sistemalima.dto.output.Veiculo
import br.com.sistemalima.enum.RegrasVeiculoVendasEnum
import br.com.sistemalima.exceptions.ClientException
import br.com.sistemalima.exceptions.InternalGenericException
import br.com.sistemalima.exceptions.VeiculoException
import br.com.sistemalima.http.VeiculoHttp
import br.com.sistemalima.model.Observabilidade
import io.micronaut.http.client.exceptions.HttpClientException
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class VendaService(
    private val veiculoHttp: VeiculoHttp
) {
    companion object {
        private val tag = "class: VendaService"
        private val tagRealizarVenda = "method: realizarVenda"
        private val veiculoExceptionMessage = "Id do veiculo não encontrado"
        private val clientExceptionMessage = "Servidor veiculo indisponivel"
        private val internalGenericExceptionMessage = "Falha no servidor interno"
    }

    private val logger = LoggerFactory.getLogger(VendaService::class.java)

    fun realizarVenda(vendaInput: VendaInput, observabilidade: Observabilidade): Veiculo {
        logger.info(String.format("$tag, $tagRealizarVenda, Movimentação do processo request: $observabilidade"))

        try {
            val idVeiculo = vendaInput.veiculo
            // comunicação entre microsserviços
            return veiculoHttp.findById(idVeiculo).get()

        }catch (ex: HttpClientException) {
            logger.error(String.format("Error: $tag, $tagRealizarVenda, $observabilidade," +
                    " exceptionMessage: ${ex.message}, message: $clientExceptionMessage cause: ${ex.cause}"))
            throw ClientException(clientExceptionMessage, RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL, ex)
        }catch (ex: NoSuchElementException) {
            logger.error(String.format("Error: $tag, $tagRealizarVenda, $observabilidade," +
                    " exceptionMessage: ${ex.message}, message: $veiculoExceptionMessage cause: ${ex.cause}"))
            throw VeiculoException(veiculoExceptionMessage, RegrasVeiculoVendasEnum.NAO_ENCONTRADO, ex)
        }catch (ex: Exception) {
            logger.error(String.format("Error: $tag, $tagRealizarVenda, $observabilidade," +
                    " exceptionMessage: ${ex.message}, message: $internalGenericExceptionMessage cause: ${ex.cause}"))
            throw InternalGenericException(internalGenericExceptionMessage, RegrasVeiculoVendasEnum.SERVIDOR_FALHA,ex)
        }
    }
}