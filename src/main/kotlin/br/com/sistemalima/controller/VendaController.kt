package br.com.sistemalima.controller

import br.com.sistemalima.dto.input.VendaInput
import br.com.sistemalima.dto.output.Venda
import br.com.sistemalima.model.Observabilidade
import br.com.sistemalima.service.VendaService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory

@Controller(value = "/vendas", produces = [MediaType.APPLICATION_JSON], consumes = [MediaType.APPLICATION_JSON])
class VendaController(
    private val vendaService: VendaService
) {

    private val logger = LoggerFactory.getLogger(VendaController::class.java)

    companion object {
        private val tag = "class: VendaController"
        private val tagRealizarVenda = "method: realizarVenda [POST]"
    }

    @Post
    fun realizarVenda(@Body vendaInput: VendaInput): HttpResponse<Venda> {
        val observabilidade = Observabilidade(vendaInput.cliente, vendaInput.veiculo)
        logger.info(String.format("$tag, ${tagRealizarVenda}, Inicio do processo request: $observabilidade"))
        val venda = vendaService.realizarVenda(vendaInput, observabilidade)
        logger.info(String.format("$tag, $tagRealizarVenda, Fim do processo request: $observabilidade"))
        return HttpResponse.ok(venda)
    }
}