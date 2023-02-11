package br.com.sistemalima.controller.handler

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType

abstract class AbstractHandler {

    abstract fun status(httpRequest: HttpRequest<*>?, exception: Exception?): HttpStatus
    open fun contentType(): String {
        return MediaType.APPLICATION_JSON
    }
    abstract fun codigo(httpRequest: HttpRequest<*>?, exception: Exception?): Int
    abstract fun mensagem(httpRequest: HttpRequest<*>?, exception: Exception?): String
    abstract fun detalhes(httpRequest: HttpRequest<*>?, exception: Exception?): ArrayList<String?>?

    protected fun detalhado(throwable: Throwable): ArrayList<String?>? {
        if (throwable == null) return null
        val list = ArrayList<String?>()
        list.add(resumido(throwable))
        var e = throwable.cause
        while (e != null) {
            list.add(resumido(e))
            e = e.cause
        }
        return list
    }

    private fun resumido(throwable: Throwable): String {
        val simple = throwable.javaClass.simpleName
        val message = throwable.message
        return if (message == null) {
            simple
        } else {
            "$simple $message"
        }
    }

    internal fun reply(httpRequest: HttpRequest<*>?, exeption: Exception?): HttpResponse<ErrorResponse>? {
        return HttpResponse.status<ErrorResponse?>(status(httpRequest, exeption)).contentType(contentType()).body(
            ErrorResponse(codigo(httpRequest, exeption), mensagem(httpRequest, exeption), detalhes(httpRequest, exeption))
        )
    }

    data class ErrorResponse(val codigo: Int, val messagem: String?, val detalhes: ArrayList<String?>?)
}