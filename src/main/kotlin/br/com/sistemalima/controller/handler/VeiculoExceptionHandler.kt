package br.com.sistemalima.controller.handler

import br.com.sistemalima.enum.RegrasVeiculoVendasEnum
import br.com.sistemalima.exceptions.VeiculoException
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
@Requires(classes = [VeiculoException::class, ExceptionHandler::class])
class VeiculoExceptionHandler: AbstractHandler(), ExceptionHandler<VeiculoException?, HttpResponse<AbstractHandler.ErrorResponse>> {

    override fun handle(httpRequest: HttpRequest<*>?, veiculoException: VeiculoException?): HttpResponse<ErrorResponse>? {
        return reply(httpRequest, veiculoException)
    }

    override fun status(httpRequest: HttpRequest<*>?, exception: Exception?): HttpStatus {
        return when (exception) {
            is VeiculoException -> {
                when(exception.regrasVeiculoVendasEnum) {
                    RegrasVeiculoVendasEnum.NAO_ENCONTRADO -> HttpStatus.NOT_FOUND
                    RegrasVeiculoVendasEnum.ESTOQUE_INDISPONIVEL -> HttpStatus.BAD_REQUEST
                    RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL -> HttpStatus.BAD_GATEWAY
                    RegrasVeiculoVendasEnum.SERVIDOR_FALHA -> HttpStatus.INTERNAL_SERVER_ERROR
                }
            } else -> {HttpStatus.INTERNAL_SERVER_ERROR}
        }
    }

    override fun codigo(httpRequest: HttpRequest<*>?, exception: Exception?): Int {
        return when(exception) {
            is VeiculoException -> {
                when(exception.regrasVeiculoVendasEnum) {
                    RegrasVeiculoVendasEnum.NAO_ENCONTRADO -> RegrasVeiculoVendasEnum.NAO_ENCONTRADO.codigo
                    RegrasVeiculoVendasEnum.ESTOQUE_INDISPONIVEL -> RegrasVeiculoVendasEnum.ESTOQUE_INDISPONIVEL.codigo
                    RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL -> RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL.codigo
                    RegrasVeiculoVendasEnum.SERVIDOR_FALHA -> RegrasVeiculoVendasEnum.SERVIDOR_FALHA.codigo
                }
            }else -> {800}
        }
    }

    override fun mensagem(httpRequest: HttpRequest<*>?, exception: Exception?): String {
        return when(exception) {
            is VeiculoException -> {
                when(exception.regrasVeiculoVendasEnum) {
                    RegrasVeiculoVendasEnum.NAO_ENCONTRADO -> "Id do Veiculo para venda não encontrado"
                    RegrasVeiculoVendasEnum.ESTOQUE_INDISPONIVEL -> "Veiculo indisponivel para venda"
                    RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL -> "Servidor Cliente Veiculo indisponivel"
                    RegrasVeiculoVendasEnum.SERVIDOR_FALHA -> "Ocorreu uma falha inesperada no servidor"
                }
            } else -> {
                "Ocorreu uma falha de negocio"
            }
        }
    }

    override fun detalhes(httpRequest: HttpRequest<*>?, exception: Exception?): ArrayList<String?>?  = exception?.let {
        detalhado(
            it
        )
    }
}