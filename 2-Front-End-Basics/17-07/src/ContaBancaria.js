class ContaBancaria { 
    constructor() {
        this.saldo = 0
    }

    consultarSaldo() {
        return this.saldo
    }

    depositar(valor) {
        if(valor <= 0) {
            throw new Error('O valor depositado deve ser maior que zero')
        }

        this.saldo += valor
    }

    sacar(valor) {
        if(valor <= 0) {
            throw new Error('O valor sacado deve ser maior que zero')
        }

        if(valor > this.saldo) {
             throw new Error('Saldo insuficiente')
        }

        this.saldo -= valor
    }
}

module.exports = ContaBancaria