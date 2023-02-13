package br.com.sistemalima.controller.handler

import br.com.sistemalima.enum.RegrasVeiculoVendasEnum
import br.com.sistemalima.exceptions.ClientException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.IOException

internal class ClientExceptionHandlerTest {

    private val clientExceptionHandler: ClientExceptionHandler = ClientExceptionHandler()

    private val httpRequest = mockk<HttpRequest<String?>>()

    companion object {
        private val testMessage = "testMessage"
    }

    @Test
    fun `status deve retornar 502 Bad Gateway, quando a RegraVeiculoVendasEnum for ClienteIndisponivel `() {

        // Dado
        val exception = ClientException(testMessage, RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL)

        // Quando
        val response = clientExceptionHandler.status(httpRequest, exception)

        // Então
        assertEquals(HttpStatus.BAD_GATEWAY, response)
        assertEquals(HttpStatus.BAD_GATEWAY.code, response.code)
    }

    @Test
    fun `status deve retornar 500 InternalServerError, quando a RegrasVeiculoVendaEnum for diferente de ClienteIndisponivel `() {

        // Dado
        val exception = ClientException(testMessage, RegrasVeiculoVendasEnum.NAO_ENCONTRADO)

        // Quando
        val response = clientExceptionHandler.status(httpRequest, exception)

        // Então
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.code, response.code)
    }

    @Test
    fun `status deve retornar 500 InternalServerError, quando a exception for IoException `() {

        // Dado
        val exception = IOException(testMessage)

        // Quando
        val response = clientExceptionHandler.status(httpRequest, exception)

        // Então
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.code, response.code)
    }

    @Test
    fun `message deve retornar a mensagem, Ocorreu um erro entre a comunicacao com o servidor Veiculo, a RegrasVeiculoVendasEnum for ClienteIndisponivel`() {

        // Dado
        val exception = ClientException(testMessage, RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL)

        // Quando
        val response = clientExceptionHandler.mensagem(httpRequest, exception)

        // Então
        assertEquals("Ocorreu um erro entre a comunicação com o servidor Veiculo", response)
    }

    @Test
    fun `message deve retornar a mensagem, Ocorreu uma falha de negocio, a RegrasVeiculoVendasEnum for diferente de ClienteIndisponivel`() {

        // Dado
        val exception = ClientException(testMessage, RegrasVeiculoVendasEnum.NAO_ENCONTRADO)

        // Quando
        val response = clientExceptionHandler.mensagem(httpRequest, exception)

        // Então
        assertEquals("Ocorreu uma falha de negocio", response)
    }

    @Test
    fun `message deve retornar a mensagem, Ocorreu uma falha de negocio, quando a excecao for IoException`() {

        // Dado
        val exception = IOException(testMessage)

        // Quando
        val response = clientExceptionHandler.mensagem(httpRequest, exception)

        // Então
        assertEquals("Ocorreu uma falha de negocio", response)
    }

    @Test
    fun `codigo deve retornar codigo 103, quando a RegraVeiculoVendasEnum for ClienteIndisponivel, exception ClientException`() {

        // Dado
        val exception = ClientException(testMessage, RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL)

        // Quando
        val response = clientExceptionHandler.codigo(httpRequest, exception)

        // Então
        assertEquals(RegrasVeiculoVendasEnum.CLIENTE_INDISPONIVEL.codigo, response)
    }

    @Test
    fun `codigo deve retornar codigo 800, quando a RegraVeiculoVendasEnum for diferente de ClienteIndisponivel, exception ClientException`() {

        // Dado
        val exception = ClientException(testMessage, RegrasVeiculoVendasEnum.NAO_ENCONTRADO)

        // Quando
        val response = clientExceptionHandler.codigo(httpRequest, exception)

        // Então
        assertEquals(800, response)
    }

    @Test
    fun `codigo deve retornar codigo 800, quando exception IoException`() {

        // Dado
        val exception = IOException(testMessage)

        // Quando
        val response = clientExceptionHandler.codigo(httpRequest, exception)

        // Então
        assertEquals(800, response)
    }

}