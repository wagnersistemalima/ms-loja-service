package br.com.sistemalima.controller.handler

import br.com.sistemalima.enum.RegrasVeiculoVendasEnum
import br.com.sistemalima.exceptions.ClientException
import br.com.sistemalima.exceptions.InternalGenericException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.IOException

internal class InternalGenericExceptionHandlerTest {

    private val internalGenericExceptionHandler: InternalGenericExceptionHandler = InternalGenericExceptionHandler()

    private val httpRequest = mockk<HttpRequest<String?>>()

    companion object {
        private val testMessage = "testMessage"
    }

    @Test
    fun `status deve retornar 500 Internal Server Erro, quando a RegraVeiculoVendasEnum for ClienteIndisponivel ServidorFalha`() {

        // Dado
        val exception = InternalGenericException(testMessage, RegrasVeiculoVendasEnum.SERVIDOR_FALHA)

        // Quando
        val response = internalGenericExceptionHandler.status(httpRequest, exception)

        // Então
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.code, response.code)
    }

    @Test
    fun `status deve retornar 500 InternalServerError, quando a RegrasVeiculoVendaEnum for diferente de ServidorFalha `() {

        // Dado
        val exception = InternalGenericException(testMessage, RegrasVeiculoVendasEnum.NAO_ENCONTRADO)

        // Quando
        val response = internalGenericExceptionHandler.status(httpRequest, exception)

        // Então
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.code, response.code)
    }

    @Test
    fun `status deve retornar 500 InternalServerError, quando a exception for IoException `() {

        // Dado
        val exception = IOException(testMessage)

        // Quando
        val response = internalGenericExceptionHandler.status(httpRequest, exception)

        // Então
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.code, response.code)
    }

    @Test
    fun `message deve retornar a mensagem, Ocorreu uma falha interna no servidor, a RegrasVeiculoVendasEnum for ServidorFalha`() {

        // Dado
        val exception = InternalGenericException(testMessage, RegrasVeiculoVendasEnum.SERVIDOR_FALHA)

        // Quando
        val response = internalGenericExceptionHandler.mensagem(httpRequest, exception)

        // Então
        assertEquals("Ocorreu uma falha interna no servidor", response)
    }

    @Test
    fun `message deve retornar a mensagem, Ocorreu uma falha de negocio, a RegrasVeiculoVendasEnum for diferente de Servidorfalha`() {

        // Dado
        val exception = InternalGenericException(testMessage, RegrasVeiculoVendasEnum.NAO_ENCONTRADO)

        // Quando
        val response = internalGenericExceptionHandler.mensagem(httpRequest, exception)

        // Então
        assertEquals("Ocorreu uma falha de negocio", response)
    }

    @Test
    fun `message deve retornar a mensagem, Ocorreu uma falha de negocio, quando a excecao for IoException`() {

        // Dado
        val exception = IOException(testMessage)

        // Quando
        val response = internalGenericExceptionHandler.mensagem(httpRequest, exception)

        // Então
        assertEquals("Ocorreu uma falha de negocio", response)
    }

    @Test
    fun `codigo deve retornar codigo 104, quando a RegraVeiculoVendasEnum for Servidorfalha, exception InternalGenericException`() {

        // Dado
        val exception = InternalGenericException(testMessage, RegrasVeiculoVendasEnum.SERVIDOR_FALHA)

        // Quando
        val response = internalGenericExceptionHandler.codigo(httpRequest, exception)

        // Então
        assertEquals(RegrasVeiculoVendasEnum.SERVIDOR_FALHA.codigo, response)
    }

    @Test
    fun `codigo deve retornar codigo 800, quando a RegraVeiculoVendasEnum for diferente de ServidorFalha, exception InternalGenericException`() {

        // Dado
        val exception = ClientException(testMessage, RegrasVeiculoVendasEnum.NAO_ENCONTRADO)

        // Quando
        val response = internalGenericExceptionHandler.codigo(httpRequest, exception)

        // Então
        assertEquals(800, response)
    }

    @Test
    fun `codigo deve retornar codigo 800, quando exception IoException`() {

        // Dado
        val exception = IOException(testMessage)

        // Quando
        val response = internalGenericExceptionHandler.codigo(httpRequest, exception)

        // Então
        assertEquals(800, response)
    }
}