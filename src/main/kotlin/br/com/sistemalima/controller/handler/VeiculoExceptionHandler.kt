package br.com.sistemalima.controller.handler

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
            is VeiculoException -> HttpStatus.BAD_REQUEST
            else -> {HttpStatus.INTERNAL_SERVER_ERROR}
        }
    }

    override fun codigo(httpRequest: HttpRequest<*>?, exception: Exception?): Int {
        return when(exception) {
            is VeiculoException -> 100
            else -> {200}
        }
    }

    override fun mensagem(httpRequest: HttpRequest<*>?, exception: Exception?): String {
        return when(exception) {
            is VeiculoException -> "Veiculo para venda não encontrado"
            else -> {"Ocorreu uma falha de negocio"}
        }
    }

    override fun detalhes(httpRequest: HttpRequest<*>?, exception: Exception?): ArrayList<String?>?  = exception?.let {
        detalhado(
            it
        )
    }
}