package br.com.sistemalima.controller.handler

import br.com.sistemalima.enum.RegrasVeiculoVendasEnum
import br.com.sistemalima.exceptions.VeiculoException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.IOException

internal class VeiculoExceptionHandlerTest {

    private val veiculoExceptionHandler: VeiculoExceptionHandler = VeiculoExceptionHandler()
    private val httpRequest = mockk<HttpRequest<String?>>()

    companion object {
        private val testMessage = "testMessage"
    }


    @Test
    fun `status deve retornar 404 NOT FOUND, quando a RegrasVeiculoVendasEnum for NaoEncontrado, exception VeiculoException`() {
        // Dado
        val exception = VeiculoException(testMessage, RegrasVeiculoVendasEnum.NAO_ENCONTRADO)

        // Quando
        val response = veiculoExceptionHandler.status(httpRequest, exception)

        // Então
        assertEquals(HttpStatus.NOT_FOUND, response)
        assertEquals(HttpStatus.NOT_FOUND.code, response.code)
    }

    @Test
    fun `status deve retornar 400 BAD REQUEST, quando a RegrasVeiculoVendasEnum for EstoqueIndisponivel, exception VeiculoException`() {
        // Dado
        val exception = VeiculoException(testMessage, RegrasVeiculoVendasEnum.ESTOQUE_INDISPONIVEL)

        // Quando
        val response = veiculoExceptionHandler.status(httpRequest, exception)

        // Então
        assertEquals(HttpStatus.BAD_REQUEST, response)
        assertEquals(HttpStatus.BAD_REQUEST.code, response.code)
    }

    @Test
    fun `status deve retornar 500 InternalServerError, quando a RegrasVeiculoVendasEnum for EstoqueIndisponivel, exception IoException`() {
        // Dado
        val exception = IOException(testMessage)

        // Quando
        val response = veiculoExceptionHandler.status(httpRequest, exception)

        // Então
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.code, response.code)
    }

    @Test
    fun `message deve retornar a mensagem, Id do Veiculo para venda nao encontrado, quando RegrasVeiculoVendasEnum for NaoEncontrado, exception VeiculoException`() {

        // Dado
        val exception = VeiculoException(testMessage, RegrasVeiculoVendasEnum.NAO_ENCONTRADO)

        // Quando
        val response = veiculoExceptionHandler.mensagem(httpRequest, exception)

        /// Então
        assertEquals("Id do Veiculo para venda não encontrado", response)
    }

    @Test
    fun `message deve retornar a mensagem, Veiculo indisponivel para venda, quando RegrasVeiculoVendasEnum for EstoqueIndisponivel, exception VeiculoException`() {

        // Dado
        val exception = VeiculoException(testMessage, RegrasVeiculoVendasEnum.ESTOQUE_INDISPONIVEL)

        // Quando
        val response = veiculoExceptionHandler.mensagem(httpRequest, exception)

        /// Então
        assertEquals("Veiculo indisponivel para venda", response)
    }

    @Test
    fun `message deve retornar a mensagem, Ocorreu uma falha de negocio, quando a exception IoException`() {

        // Dado
        val exception = IOException(testMessage)

        // Quando
        val response = veiculoExceptionHandler.mensagem(httpRequest, exception)

        /// Então
        assertEquals("Ocorreu uma falha de negocio", response)
    }

    @Test
    fun `codigo deve retornar codigo 101, quando a RegrasVeiculoVendasEnum for NaoEncontrado, exception VeiculoException`() {

        // Dado
        val exception = VeiculoException(testMessage, RegrasVeiculoVendasEnum.NAO_ENCONTRADO)

        // Quando
        val response = veiculoExceptionHandler.codigo(httpRequest, exception)

        // Então
        assertEquals(RegrasVeiculoVendasEnum.NAO_ENCONTRADO.codigo, response)

    }

    @Test
    fun `codigo deve retornar codigo 102, quando a RegrasVeiculoVendasEnum for EstoqueIndisponivel, exception VeiculoException`() {

        // Dado
        val exception = VeiculoException(testMessage, RegrasVeiculoVendasEnum.ESTOQUE_INDISPONIVEL)

        // Quando
        val response = veiculoExceptionHandler.codigo(httpRequest, exception)

        // Então
        assertEquals(RegrasVeiculoVendasEnum.ESTOQUE_INDISPONIVEL.codigo, response)

    }

    @Test
    fun `codigo deve retornar codigo 800, quando a exception IoException`() {

        // Dado
        val exception = IOException(testMessage)

        // Quando
        val response = veiculoExceptionHandler.codigo(httpRequest, exception)

        // Então
        assertEquals(800, response)

    }
}