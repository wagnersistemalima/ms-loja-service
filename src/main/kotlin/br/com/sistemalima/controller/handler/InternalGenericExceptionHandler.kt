package br.com.sistemalima.controller.handler

import br.com.sistemalima.enum.RegrasVeiculoVendasEnum
import br.com.sistemalima.exceptions.InternalGenericException
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
@Requires(classes = [InternalGenericException::class, ExceptionHandler::class])
class InternalGenericExceptionHandler: AbstractHandler(), ExceptionHandler<InternalGenericException?, HttpResponse<AbstractHandler.ErrorResponse>> {

    override fun handle(httpRequest: HttpRequest<*>?, internalGenericException: InternalGenericException?): HttpResponse<ErrorResponse>? {
        return reply(httpRequest, internalGenericException)
    }

    override fun status(httpRequest: HttpRequest<*>?, exception: Exception?): HttpStatus {
        return when (exception) {
            is InternalGenericException -> {
                when(exception.regrasVeiculoVendasEnum) {
                    RegrasVeiculoVendasEnum.SERVIDOR_FALHA -> HttpStatus.INTERNAL_SERVER_ERROR
                    else -> {HttpStatus.INTERNAL_SERVER_ERROR}
                }
            }else -> {HttpStatus.INTERNAL_SERVER_ERROR}
        }
    }

    override fun codigo(httpRequest: HttpRequest<*>?, exception: Exception?): Int {
        return when(exception) {
            is InternalGenericException -> {
                when(exception.regrasVeiculoVendasEnum) {
                    RegrasVeiculoVendasEnum.SERVIDOR_FALHA -> RegrasVeiculoVendasEnum.SERVIDOR_FALHA.codigo
                    else -> {800}
                }
            }else -> {800}
        }
    }

    override fun mensagem(httpRequest: HttpRequest<*>?, exception: Exception?): String {
        return when(exception) {
            is InternalGenericException -> {
                when(exception.regrasVeiculoVendasEnum) {
                    RegrasVeiculoVendasEnum.SERVIDOR_FALHA -> "Ocorreu uma falha interna no servidor"
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