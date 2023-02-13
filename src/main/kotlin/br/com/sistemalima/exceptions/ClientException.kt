package br.com.sistemalima.exceptions

import br.com.sistemalima.enum.RegrasVeiculoVendasEnum

class ClientException: NoStacktraceException {

    val regrasVeiculoVendasEnum: RegrasVeiculoVendasEnum

    constructor(message: String, anRegrasVeiculoVendasEnum: RegrasVeiculoVendasEnum) : super(message) {
        this.regrasVeiculoVendasEnum = anRegrasVeiculoVendasEnum
    }
    constructor(message: String, anRegrasVeiculoVendasEnum: RegrasVeiculoVendasEnum, throwable: Throwable) : super(message, throwable) {
        this.regrasVeiculoVendasEnum = anRegrasVeiculoVendasEnum
    }
}