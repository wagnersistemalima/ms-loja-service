package br.com.sistemalima.controller.handler

import br.com.sistemalima.exceptions.HttpClientException
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
@Requires(classes = [HttpClientException::class, ExceptionHandler::class])
class HttpExceptionHandler: AbstractHandler(), ExceptionHandler<HttpClientException?, HttpResponse<AbstractHandler.ErrorResponse>> {

    override fun handle(httpRequest: HttpRequest<*>?, httpClientException: HttpClientException?): HttpResponse<ErrorResponse>? {
        return reply(httpRequest, httpClientException)
    }

    override fun status(httpRequest: HttpRequest<*>?, exception: Exception?): HttpStatus {
        return when (exception) {
            is HttpClientException -> HttpStatus.BAD_GATEWAY
            else -> {HttpStatus.INTERNAL_SERVER_ERROR}
        }
    }

    override fun codigo(httpRequest: HttpRequest<*>?, exception: Exception?): Int {
        return when(exception) {
            is HttpClientException -> 101
            else -> {200}
        }
    }

    override fun mensagem(httpRequest: HttpRequest<*>?, exception: Exception?): String {
        return when(exception) {
            is HttpClientException -> "Ocorreu um erro entre a comunicação com o servidor Veiculo"
            else -> {"Ocorreu uma falha de negocio"}
        }
    }

    override fun detalhes(httpRequest: HttpRequest<*>?, exception: Exception?): ArrayList<String?>?  = exception?.let {
        detalhado(
            it
        )
    }
}