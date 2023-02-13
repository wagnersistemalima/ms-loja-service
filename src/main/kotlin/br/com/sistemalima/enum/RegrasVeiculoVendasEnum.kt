package br.com.sistemalima.enum

enum class RegrasVeiculoVendasEnum(val codigo: Int) {

    NAO_ENCONTRADO(101),
    ESTOQUE_INDISPONIVEL(102),
    CLIENTE_INDISPONIVEL(103),
    SERVIDOR_FALHA(104)
}