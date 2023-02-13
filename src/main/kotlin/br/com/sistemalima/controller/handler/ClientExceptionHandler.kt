package br.com.sistemalima.controller.handler

import br.com.sistemalima.enum.RegrasVeiculoVendasEnum
import br.com.sistemalima.exceptions.ClientException
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
@Requires(classes = [ClientException::class, ExceptionHandler::class])
class ClientExceptionHandler: AbstractHandler(), ExceptionHandler<ClientException?, HttpResponse<AbstractHandler.ErrorResponse>> {

    override fun handle(httpRequest: HttpRequest<*>?, clientException: ClientException?): HttpResponse<ErrorResponse>? {
        return reply(httpRequest, clientException)
    }

    override fun status(httpRequest: HttpRequest<*>?, exception: Exception?): HttpStatus {
        return when (exception) {
            is ClientException -> {
                when(exception.regrasVeiculoVendasEnum) {
                    RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL -> HttpStatus.BAD_GATEWAY
                    else -> {HttpStatus.INTERNAL_SERVER_ERROR}
                }
            }else -> {HttpStatus.INTERNAL_SERVER_ERROR}
        }
    }

    override fun codigo(httpRequest: HttpRequest<*>?, exception: Exception?): Int {
        return when(exception) {
            is ClientException -> {
                when(exception.regrasVeiculoVendasEnum) {
                    RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL -> RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL.codigo
                    else -> {800}
                }
            } else -> {800}
        }
    }

    override fun mensagem(httpRequest: HttpRequest<*>?, exception: Exception?): String {
        return when(exception) {
            is ClientException -> {
                when(exception.regrasVeiculoVendasEnum) {
                    RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL -> "Ocorreu um erro entre a comunicação com o servidor Veiculo"
                    else -> {"Ocorreu uma falha de negocio"}
                }
            }else -> {"Ocorreu uma falha de negocio"}
        }
    }

    override fun detalhes(httpRequest: HttpRequest<*>?, exception: Exception?): ArrayList<String?>?  = exception?.let {
        detalhado(
            it
        )
    }
}