package br.com.sistemalima.http

import br.com.sistemalima.dto.output.Veiculo
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import java.util.Optional

@Client(id = "http://localhost:8080")
interface VeiculoHttp {

    @Get("/veiculos/{id}")
    fun findById(@PathVariable id: Long): Optional<Veiculo>
}