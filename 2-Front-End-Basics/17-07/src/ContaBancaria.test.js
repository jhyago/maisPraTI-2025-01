const ContaBancaria = require('./ContaBancaria')

describe('Testes da class ContaBancaria', () => {
    let conta 

    beforeEach(() => {
        conta = new ContaBancaria()
    })

    test('Saldo inicial deve ser 0', () => {
        expect(conta.consultarSaldo()).toBe(0)
    })

    test('Depositar 100 deve resultar em saldo 100', () => {
        conta.depositar(100)
        expect(conta.consultarSaldo()).toBe(100)
    })
})