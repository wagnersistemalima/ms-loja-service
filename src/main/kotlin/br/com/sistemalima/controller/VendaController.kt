package br.com.sistemalima.controller

import br.com.sistemalima.dto.input.VendaInput
import br.com.sistemalima.dto.output.Veiculo
import br.com.sistemalima.model.Observabilidade
import br.com.sistemalima.service.VendaService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory

@Controller("/vendas")
class VendaController(
    private val vendaService: VendaService
) {

    private val logger = LoggerFactory.getLogger(VendaController::class.java)

    @Post
    fun realizarVenda(@Body vendaInput: VendaInput): HttpResponse<Veiculo> {
        val observabilidade = Observabilidade(vendaInput.cliente, vendaInput.veiculo)
        logger.info("Inicio do processo request: $observabilidade")
        val veiculo = vendaService.realizarVenda(vendaInput, observabilidade)
        logger.info("Fim do processo request: $observabilidade")
        return HttpResponse.ok<Veiculo?>().body(veiculo)
    }
}