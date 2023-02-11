package br.com.sistemalima.model

import java.util.UUID

data class Observabilidade(
    val client: String,
    val idVeiculo: Long,
    val correlationId: String = UUID.randomUUID().toString()
)
